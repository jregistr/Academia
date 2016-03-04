package metermen.server.udp

import java.net.{DatagramPacket, DatagramSocket, InetAddress}

import metermen.constants.Constants.MAX_UDP_PACKET_SIZE

abstract class UDPServer(size: Int, localPort: Int, destUri: String, destPort: Int) {
  private val packetSize = (size.toDouble / Math.ceil(size / MAX_UDP_PACKET_SIZE).toInt).toInt

  protected val socket = new DatagramSocket(localPort, InetAddress.getByName("localhost"))

  protected val writePacket =
    if (size > MAX_UDP_PACKET_SIZE)
      new DatagramPacket(new Array[Byte](packetSize), 0, packetSize,
        InetAddress.getByName(destUri), destPort)
    else
      new DatagramPacket(new Array[Byte](size), 0, size,
        InetAddress.getByName(destUri), destPort)

  protected val readPacket =
    if (size > MAX_UDP_PACKET_SIZE)
      new DatagramPacket(new Array[Byte](packetSize), 0, packetSize)
    else
      new DatagramPacket(new Array[Byte](size), 0, size)

  /*protected val writePackets = {
    val buffer = new ListBuffer[DatagramPacket]
    val endINetAddress = InetAddress.getByName(destUri)
    loop(loopCount, () => {
      buffer += new DatagramPacket(new Array[Byte](packetSize),
        0, packetSize, endINetAddress, destPort)
    })
    buffer.toList
  }*/

  /*protected val readPackets = {
    val buffer = new ListBuffer[DatagramPacket]
    loop(loopCount, () => {
      buffer += new DatagramPacket(new Array[Byte](packetSize), 0, packetSize)
    })
    buffer.toList
  }*/

  def process(): Unit

}
