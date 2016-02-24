package metermen

import metermen.servers.tcp.TCPEcho

/**
  * Class to act as the main entry point into the program. Takes input for which server to start.
  */
object ServerMain {
  def main(args: Array[String]) {
    println ("Welcome to server creator. Choose which server to start up!!!")
    println("Choices are: {Latency-TCP[0]")
    TCPEcho(7000).start()
  }
}
