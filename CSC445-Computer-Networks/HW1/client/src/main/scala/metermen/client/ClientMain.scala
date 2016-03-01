package metermen.client

import metermen.client.tcp.{TCPMessagesClient, TCPThroughClient, TCPEchoClient}

import scala.io.StdIn.{readLine => getInput}

/**
  * Client main
  */
object ClientMain {
  def main(args: Array[String]) {
    println("Welcome to client manager.")
    println("What Address to connect to??")
    val add = "localhost"

    println("What port to use for connection??")
    val port = 7000

    println("Now which client do you want to start??")
    println("Choices are: TCP-RTT[0], TCP-Throughput[1]")
    val client = 2

    client match {
      case 0 => new TCPEchoClient(add, port, "TCP-RTT").process()
      case 1 => new TCPThroughClient(add, port, "TCP-Though").process()
      case 2 => new TCPMessagesClient(add, port, "TCP-Message").process()
      case _ => throw new IllegalArgumentException
    }
  }
}
