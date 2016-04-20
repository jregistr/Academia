package com.jeff.megaupload.client.clients

import java.net.{DatagramPacket, DatagramSocket, InetAddress}

import com.jeff.megaupload.constant.Constants._

/**
  * Class to represent an abstract transfer client.
  *
  * @param localAddress The local address for this client.
  * @param localPort    The local port for this client.
  */
abstract class Client(localAddress: String, localPort: Int) {

  protected val socket = new DatagramSocket(localPort, InetAddress.getByName(localAddress))
  protected val readPacket = new DatagramPacket(new Array[Byte](PACKET_SIZE), 0, PACKET_SIZE)
  protected val writePacket = new DatagramPacket(new Array[Byte](PACKET_SIZE), 0, PACKET_SIZE)

  protected val PACKETS_IN_CHUNKS = 100000
  protected val windowSize = 500
  socket.setSoTimeout(1000)

  /**
    * Method to call to make a packet from the given data.
    *
    * @param seq         The sequence number.
    * @param bytes       The payload data.
    * @param destAddress The destination address.
    * @param destPort    The destination port.
    * @return The packet.
    */
  protected def asPacket(seq: Int, bytes: Array[Byte], destAddress: InetAddress, destPort: Int): DatagramPacket


}
