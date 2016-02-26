package metermen

import metermen.clients.tcp.TCPEchoClient
import scala.io.StdIn.{readLine => getInput}

/**
  * Client main
  */
object ClientMain {
  def main(args: Array[String]) {
    println("Client Manager. Possible Clients:TCPEcho[0]\n")
    println("Enter Address and port of server")
    val add = getInput()
    val port = getInput().toInt
    TCPEchoClient(add, port).process()
  }
}
