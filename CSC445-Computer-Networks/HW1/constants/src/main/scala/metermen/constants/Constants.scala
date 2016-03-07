package metermen.constants

import java.net.ServerSocket

import scala.collection.mutable.{Map => MutMap, ListBuffer}
import scala.math.BigDecimal.RoundingMode

/**
  * Class to hold constants shared throughout project
  */
object Constants {

  val NANOS_TO_MILIS = 0.000001f
  val MILIS_TO_NANOS = 1000000
  val NANOS_TO_SECONDS = 0.0000000001f
  val MAX_UDP_PACKET_SIZE = 32000
  private val sizes = List("B", "KB", "MB", "GB", "TB", "PB")

  object TCPEchoSize extends Enumeration {
    val ONE = Value(1)
    val THIRTY_TWO = Value(32)
    val TEN_TWN4 = Value(1024)
  }

  def udpPacketSizeConfig(totalSize: Int): List[(Int,Int)] = {
    val mutMap = new ListBuffer[(Int,Int)]
    if (totalSize <= MAX_UDP_PACKET_SIZE) {
      mutMap += (totalSize -> 1)
    } else {
      val fit = totalSize / MAX_UDP_PACKET_SIZE
      val remain = totalSize % MAX_UDP_PACKET_SIZE
      mutMap += MAX_UDP_PACKET_SIZE -> fit
      if (remain > 0)
        mutMap += remain -> 1
    }
    mutMap.toList
  }

  def availablePort(port: Int): Boolean = {
    var b: Boolean = false
    try {
      new ServerSocket(port).close()
      b = true
    } catch {
      case e: Exception => b = false
    }
    b
  }

  def bytesToSize(bytes: Int): String = {
    if (bytes == 0) {
      "0 B"
    } else {
      val i = Math.floor(Math.log(bytes) / Math.log(1024)).toInt
      if (i == 0) {
        s"$bytes ${sizes(i)}"
      } else {
        val round = BigDecimal(bytes / Math.pow(1024, i)).setScale(2, RoundingMode.HALF_UP).toDouble
        s"$round ${sizes(i)}"
      }
    }
  }

}
