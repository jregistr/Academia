package com.jeff.megaupload.server.servers

import java.net.{DatagramPacket, DatagramSocket, InetAddress}
import java.util.concurrent.ThreadLocalRandom

import com.jeff.megaupload.constant.Constants
import com.jeff.megaupload.constant.Constants.PACKET_SIZE

/**
  * Class to represent an abstract server.
  */
abstract class Server(port: Int, localAddress: String, val simDrops: Boolean) {

  protected val socket = new DatagramSocket(port, InetAddress.getLocalHost)
  protected val readPacket = new DatagramPacket(new Array[Byte](PACKET_SIZE), 0, PACKET_SIZE)

  private val random = ThreadLocalRandom.current()

  protected def sendAck(ack: Int, destAdd: String, destPort: Int): Unit = {
    socket.send(new DatagramPacket(Constants.intToByteArray(ack), 0, Constants.INT_BYTES, InetAddress.getByName(destAdd), destPort))
  }

  /**
    * Method to extract the sequence number and payload from a packet.
    *
    * @param packet The packet.
    * @return A tuple containing the sequence number and the payload.
    */
  protected final def seqAndPayload(packet: DatagramPacket): (Int, Array[Byte]) = {
    val raw = packet.getData
    val seq = Constants.byteArrayToInt(raw.slice(0, Constants.INT_BYTES))
    val countStartIndex = Constants.INT_BYTES
    val endCountIndex = countStartIndex + Constants.INT_BYTES
    val count = Constants.byteArrayToInt(raw.slice(countStartIndex, endCountIndex))
    val payLoad = raw.slice(endCountIndex, endCountIndex + count)
    (seq, payLoad)
  }

  /**
    * Method to transform a packet into desired data form.
    *
    * @param packet The packet.
    * @return A tuple containing the sequence number and the payload.
    */
  protected def fromPacket(packet: DatagramPacket): (Int, Array[Byte])

  /**
    * Method to determine if a drop should be simulated.
    * @return True to simulate a drop.
    */
  protected final def drop: Boolean = !simDrops && (random.nextInt(101) <= 10)

}
