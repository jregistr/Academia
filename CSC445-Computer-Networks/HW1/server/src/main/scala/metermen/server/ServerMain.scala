package metermen.server

import metermen.constants.Constants.availablePort
import metermen.server.tcp.{TCPMessagesServer, TCPEchoServer, TCPThroughServer}
import metermen.server.udp.UDPEchoServer
import com.jeff.dsl.util.Util._

import scala.collection.mutable.ListBuffer
import scala.io.StdIn.{readLine => getInput}

/**
  * Class to act as the main entry point into the program. Takes input for which server to start.
  */
object ServerMain {
  def main(args: Array[String]) {

    //Simplify this. do tests one by one. including each piece of the udp ones

    println("Welcome to server creator.")

    println("What port to run Echo Server on??")
    val echoServerPort = getInput().toInt

    println("What port to run Throughput Server on??")
    val throughputServerPort = getInput().toInt

    println("What port to run Messages Server on??")
    val messagesServerPort = getInput().toInt

    val udpTups = new ListBuffer[(Int, Int, String, Int)]

    println("8 Tuple(3) of the form below must be entered:")
    println("Size(Int),ServerPort(Int),ClientAddress(String),ClientPort(Int)")
    println("Insert them now")

    loop(8, () => {
      val input = getInput()
      val split = input.split(",")
      udpTups += ((split(0).toInt, split(1).toInt, split(2), split(3).toInt))
    })

    //Check that all provided local ports are available.
    {
      val checkBuffer = new ListBuffer[(Int, Boolean)]
      checkBuffer += ((echoServerPort, availablePort(echoServerPort)))
      checkBuffer += ((throughputServerPort, availablePort(throughputServerPort)))
      checkBuffer += ((messagesServerPort, availablePort(messagesServerPort)))
      udpTups.foreach(x => {
        checkBuffer += ((x._2, availablePort(x._2)))
      })
      var allGood = true
      checkBuffer.foreach(x => {
        if (!x._2) {
          allGood = false
          println(s"PORT NUMBER:${x._1} is not available")
        }
      })

      allGood match {
        case false => throw new IllegalArgumentException()
        case true =>
      }
    }

    val threads = new ListBuffer[Thread]

    threads += new Thread {
      override def run(): Unit = {
        TCPEchoServer(echoServerPort)
      }
    }

    threads += new Thread() {
      TCPThroughServer(throughputServerPort)
    }

    threads += new Thread() {
      TCPMessagesServer(messagesServerPort)
    }

    udpTups.foreach(udpTup => {
      threads += new Thread(){
        new UDPEchoServer(udpTup._1, udpTup._2, udpTup._3, udpTup._4)
      }
    })

    threads.foreach(x=>x.start())
    threads.foreach(x=>x.join())
  }


}
