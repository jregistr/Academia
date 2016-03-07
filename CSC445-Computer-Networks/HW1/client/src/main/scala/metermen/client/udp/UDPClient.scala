package metermen.client.udp

import java.net.{DatagramPacket, DatagramSocket, InetAddress}
import scala.collection.mutable.{Map => MutMap}

import metermen.constants.Constants


abstract class UDPClient(size: Int, localPort: Int, destUri: String, destPort: Int) {

  final val TEST_COUNT = 1000
  final val TIME_OUT = 3000

  protected val socket = new DatagramSocket(localPort, InetAddress.getByName("localhost"))

  private val config = Constants.udpPacketSizeConfig(size)

  protected val writePackets = {
    val buffer = Map.newBuilder[DatagramPacket, Int]
    val endInetAddress = InetAddress.getByName(destUri)
    config.foreach(c => {
      buffer += new DatagramPacket(new Array[Byte](c._1), 0, c._1, endInetAddress, destPort) -> c._2
    })
    buffer.result()
  }

  protected val readPackets = {
    val buffer = Map.newBuilder[DatagramPacket, Int]
    config.foreach(
      c => buffer += new DatagramPacket(new Array[Byte](c._1), 0, c._1) -> c._2
    )
    buffer.result()
  }

  protected val keeper = {
    val buffer = MutMap[DatagramPacket, Int]()
    readPackets.foreach(c => buffer += c._1 -> 0)
    buffer
  }

  protected final def resetKeeper(): Unit = {
    keeper.foreach(keeper += _._1 -> 0)
  }

  protected final def writePacketForSize(size: Int): DatagramPacket = {
    val packet = writePackets.find(_._1.getLength == size)
    if (packet.isDefined) {
      packet.get._1
    } else {
      throw new NullPointerException
    }
  }

  def process(): (String, List[Double])

}


