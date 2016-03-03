package metermen.client.udp

import java.net.{DatagramPacket, DatagramSocket, InetAddress}

import metermen.constants.Constants.MAX_UDP_PACKET_SIZE

import scala.collection.mutable.ListBuffer
import com.jeff.dsl.util.Util._


abstract class UDPClient(size: Int, uri: String, port: Int, val name: String) {

  private val iNetAddress = InetAddress.getByName(uri)
  private val loopCount = Math.ceil(size / MAX_UDP_PACKET_SIZE).toInt
  private val packetSize = (size.toDouble / loopCount).toInt

  protected val socket = new DatagramSocket(port, iNetAddress)

  protected val writePackets = {
    val buffer = new ListBuffer[DatagramPacket]
    loop(loopCount, () => {
      buffer += new DatagramPacket(new Array[Byte](packetSize), 0, packetSize, iNetAddress, port)
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

  def process():(String, List[Double])

}


