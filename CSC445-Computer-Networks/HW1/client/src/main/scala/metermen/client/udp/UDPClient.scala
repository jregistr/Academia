package metermen.client.udp

import java.net.{DatagramPacket, DatagramSocket, InetAddress}

import com.jeff.dsl.util.Util._
import metermen.constants.Constants.MAX_UDP_PACKET_SIZE

import scala.collection.mutable.ListBuffer


abstract class UDPClient(size: Int, localPort: Int, destAddress: String, destPort: Int, val name: String) {

  final val TEST_COUNT = 10000
  final val TIME_OUT = 5000

  private val loopCount = Math.ceil(size / MAX_UDP_PACKET_SIZE).toInt
  private val packetSize = (size.toDouble / loopCount).toInt

  protected val socket = new DatagramSocket(localPort, InetAddress.getByName("localhost"))

  protected val writePackets = {
    val buffer = new ListBuffer[DatagramPacket]
    val endInetAddress = InetAddress.getByName(destAddress)
    loop(loopCount, () => {
      buffer += new DatagramPacket(new Array[Byte](packetSize), 0, packetSize, endInetAddress, destPort)
    })
    buffer.toList
  }

  protected val readPackets = {
    val buffer = new ListBuffer[DatagramPacket]
    loop(loopCount, () => {
      buffer += new DatagramPacket(new Array[Byte](packetSize), 0, packetSize)
    })
    buffer.toList
  }

  def process(): (String, List[Double])

}


