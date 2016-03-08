package metermen.server.udp

import java.net.{DatagramPacket, DatagramSocket, InetAddress}

import metermen.constants.Constants.MAX_UDP_PACKET_SIZE

abstract class UDPServer(size: Int, localPort: Int) {
  private val packetSize = (size.toDouble / Math.ceil(size / MAX_UDP_PACKET_SIZE).toInt).toInt

  protected val socket = new DatagramSocket(localPort, InetAddress.getByName("wolf.cs.oswego.edu"))

  protected val writePacket =
    if (size > MAX_UDP_PACKET_SIZE)
      new DatagramPacket(new Array[Byte](packetSize), 0, packetSize)
    else
      new DatagramPacket(new Array[Byte](size), 0, size)

  protected val readPacket =
    if (size > MAX_UDP_PACKET_SIZE)
      new DatagramPacket(new Array[Byte](packetSize), 0, packetSize)
    else
      new DatagramPacket(new Array[Byte](size), 0, size)

  def process(): Unit

}
