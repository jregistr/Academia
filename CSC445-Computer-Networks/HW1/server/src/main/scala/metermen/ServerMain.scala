package metermen

import metermen.servers.tcp.TCPEcho
import scala.io.StdIn.{readLine => getInput}
/**
  * Class to act as the main entry point into the program. Takes input for which server to start.
  */
object ServerMain {
  def main(args: Array[String]) {
    println ("Welcome to server creator.")

    println("What port is this running on???")
    val port = getInput().toInt

    println("Choose which server to start up!!!")
    println("Choices are: {Latency-TCP[0]")
    TCPEcho(port).start()
  }
}
