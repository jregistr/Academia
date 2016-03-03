package metermen.constants

/**
  * Class to hold constants shared throughout project
  */
object Constants {

  val NANOS_TO_MILIS = 0.000001f
  val MAX_UDP_PACKET_SIZE = 32000

  object TCPEchoSize extends Enumeration{
    val ONE = Value(1)
    val THIRTY_TWO = Value(32)
    val TEN_TWN4 = Value(1024)
  }

}
