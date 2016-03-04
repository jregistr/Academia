package metermen.server

import com.jeff.dsl.util.Util._
import metermen.server.tcp.{TCPEchoServer, TCPMessagesServer, TCPThroughServer}
import metermen.server.udp.UDPEchoServer

import scala.collection.mutable.ListBuffer
import scala.io.StdIn.{readLine => getInput}

/**
  * Class to act as the main entry point into the program. Takes input for which server to start.
  */
object ServerMain {
  def main(args: Array[String]) {

    println("Server creator program start")
    println("What type of server to start? [0]:TCP, [1]:UDP")
    val sType = getInput().toInt

    if (sType == 0) {

      println("TCP!!")
      println("What port to run server on??")
      val port = getInput().toInt
      println("Select which server to start:")
      println("[0]:RTT, [1]:Throughput, [2]:MessageSize")
      val si = getInput().toInt

      si match {
        case 0 =>
          println("ECHO")
          TCPEchoServer(port).connectionWait()
        case 1 => TCPThroughServer(port).connectionWait()
        case 2 => TCPMessagesServer(port).connectionWait()
        case _ => throw new IllegalArgumentException
      }

    } else {

      println("UDP!!")
      println("How many sets of tests to run??")
      val tests = getInput().toInt
      println(s"Okay. ($tests) tuples of the form: Size(Int),localPort(Int),destAddress(String),destPort(Int) are required.")
      println("Enter them now")

      // val tuples = new ListBuffer[(Int, Int, String, Int)]
      val threads = new ListBuffer[Thread]
      loop(tests, () => {
        val split = getInput().split(",")
        threads += new Thread() {
          new UDPEchoServer(split(0).toInt, split(1).toInt, split(2), split(3).toInt).process()
        }
      })

      threads.foreach(_.start())
      threads.foreach(_.join())

    }

  }


}
