package metermen.client

import com.jeff.dsl.util.Util._
import metermen.client.tcp.{TCPEchoClient, TCPMessagesClient, TCPThroughClient}
import metermen.client.udp.UDPClientRTT
import metermen.client.util.CSVMan

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.io.StdIn.{readLine => getInput}

/**
  * Client main
  */
object ClientMain {
  def main(args: Array[String]) {
    println("Client Creator")
    println("Which kind of client to run? [0]:TCP, [1]:UDP")
    val cType = getInput().toInt
    if (cType == 0) {
      println("TCP")
      println("What address and port client is connecting to?")
      println("Enter as tuple of the form (Address(String),Port(Int))")
      val addPort = getInput().split(",")
      println("Which Client to start:[0]:RTT, [1]:Throughput, [2]:Messages")
      getInput().toInt match {
        case 0 =>
          val client = new TCPEchoClient(addPort(0), addPort(1).toInt, "TCP-RTT")
          CSVMan.write(client.fileName(), client.process())
        case 1 =>
          val client = new TCPThroughClient(addPort(0), addPort(1).toInt, "TCP-Throughput")
          CSVMan.write(client.fileName(), client.process())
        case 2 =>
          val client = new TCPMessagesClient(addPort(0), addPort(1).toInt, "TCP-Messages")
          CSVMan.write(client.fileName(), client.process())
        case _ => throw new IllegalArgumentException
      }
    } else {
      println("UDP")
      println("Enter the name of the test suite")
      val name = s"./${getInput()}.csv"
      println("How many sets of tests to run??")
      val tests = getInput().toInt

      val results = new ListBuffer[(String, List[Double])]

      println(s"$tests tuples of the form Size(Int), localPort(Int), destAddress(String), destPort(Int)")
      println("Enter them now")

      loop(tests, () => {

        val line = getInput().split(",")

        Future {
          new UDPClientRTT(line(0).toInt, line(1).toInt, line(2), line(3).toInt).process()
        }.onComplete(value => {
          value.isSuccess match {
            case true =>
              results.synchronized {
                results += value.get
                if (results.size == tests) {
                  println("I am true")
                  sendToCSVMan(name, results.toList)
                }
              }
            case _ => throw new NullPointerException("Value not available")
          }
        })
      })

    }
  }

  def sendToCSVMan(name: String, values: List[(String, List[Double])]): Unit = {
    CSVMan.write(name, values)
  }


}
