package metermen.server

import metermen.server.tcp.{TCPEchoServer, TCPThroughServer}

import scala.io.StdIn.{readLine => getInput}
/**
  * Class to act as the main entry point into the program. Takes input for which server to start.
  */
object ServerMain {
  def main(args: Array[String]) {
    println ("Welcome to server creator.")

    println("What port is this running on???")
    val port = 65000

    println("Choose which server to start up!!!")
    println("Choices are: {TCP-RTT[0], TCP-Throughput[1]")
    val server = 1

    server match {
      case 0 => TCPEchoServer(port).connectionWait()
      case 1 => TCPThroughServer(port).connectionWait()
      case _ => throw new IllegalArgumentException
    }

    TCPEchoServer(port).connectionWait()
  }
}
