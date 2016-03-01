package metermen.constants

/**
  * Class to hold constants shared throughout project
  */
object Constants {

  val NANO_TO_MILIS = 0.000001f

  object TCPEchoSize extends Enumeration{
    val ONE = Value(1)
    val THIRTY_TWO = Value(32)
    val TEN_TWN4 = Value(1024)
  }

}
