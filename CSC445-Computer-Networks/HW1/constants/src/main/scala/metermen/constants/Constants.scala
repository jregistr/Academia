package metermen.constants

import java.net.{ServerSocket, Socket}

import scala.math.BigDecimal.RoundingMode

/**
  * Class to hold constants shared throughout project
  */
object Constants {

  val NANOS_TO_MILIS = 0.000001f
  val NANOS_TO_SECONDS = 0.0000000001f
  val MAX_UDP_PACKET_SIZE = 32000f
  private val sizes = List("B", "KB", "MB", "GB", "TB", "PB")

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

  def bytesToSize(bytes:Int):String = {
    if(bytes == 0){
      "0 B"
    }else{
      val i = Math.floor(Math.log(bytes) / Math.log(1024)).toInt
      if(i == 0){
        s"$bytes ${sizes(i)}"
      }else{
        val round = BigDecimal(bytes / Math.pow(1024, i)).setScale(2, RoundingMode.HALF_UP).toDouble
        s"$round ${sizes(i)}"
      }
    }
  }

}
