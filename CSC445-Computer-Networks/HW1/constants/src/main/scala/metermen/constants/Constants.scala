package metermen.constants

import java.net.{ServerSocket, Socket}

/**
  * Class to hold constants shared throughout project
  */
object Constants {

  val NANOS_TO_MILIS = 0.000001f
  val MAX_UDP_PACKET_SIZE = 32000f

  object TCPEchoSize extends Enumeration {
    val ONE = Value(1)
    val THIRTY_TWO = Value(32)
    val TEN_TWN4 = Value(1024)
  }

  def availablePort(port: Int): Boolean = {
    var b: Boolean = false
    try {
      new ServerSocket(port).close()
      b = true
    } catch {
      case e: Exception =>
        println("HERE BRAH")
        b = false
    }
    b
  }

}
