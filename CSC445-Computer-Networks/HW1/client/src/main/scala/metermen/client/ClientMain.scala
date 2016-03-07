package metermen.client

import com.jeff.dsl.util.Util._
import metermen.client.tcp.{TCPEchoClient, TCPMessagesClient, TCPThroughClient}
import metermen.client.udp.UDPClientRTT
import metermen.client.util.CSVMan

import scala.collection.mutable.ListBuffer
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
      println("Half Time or no half time??")
      val half = getInput().toBoolean
      println("Enter the name of the test suite")
      val name = s"./${getInput()}.csv"
      println("Name:" + name)
      println("How many sets of tests to run??")
      val tests = getInput().toInt

      val results = new ListBuffer[(String, List[Double])]
      println(s"Enter $tests Tuples of form Size(Int), localPort(Int), destAddress(String), destPort(Int)")

      loop(tests, (index) => {

        println(s"Running ${index + 1}/$tests Tests")
        println("Enter Tuple of form Size(Int), localPort(Int), destAddress(String), destPort(Int)")

        val line = getInput().split(",")
        results += new UDPClientRTT(line(0).toInt, line(1).toInt, line(2), line(3).toInt, half).process()
        println("Done!!")
      })

      sendToCSVMan(name, results.toList)
    }
  }

  def sendToCSVMan(name: String, values: List[(String, List[Double])]): Unit = {
    CSVMan.write(name, values)
  }


}
