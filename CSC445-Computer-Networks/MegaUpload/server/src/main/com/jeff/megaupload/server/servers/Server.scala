package com.jeff.megaupload.server.servers

import java.net.{DatagramPacket, DatagramSocket, InetAddress}
import java.nio.ByteBuffer
import java.util.concurrent.ThreadLocalRandom

import com.jeff.megaupload.constant.Constants.PACKET_SIZE
import com.jeff.megaupload.constant.{Constants, Flags}
import com.jeff.megaupload.server.util.scribe.Scribe

/**
  * Class to represent an abstract server.
  *
  * @param port         The port this server runs on.
  * @param localAddress The local address of this server.
  * @param simDrops     True to simulate dropping 1% packets.
  */
abstract class Server(port: Int, localAddress: String, val simDrops: Boolean) {

  protected val socket = new DatagramSocket(port, InetAddress.getByName(localAddress))
  protected val readPacket = new DatagramPacket(new Array[Byte](PACKET_SIZE), 0, PACKET_SIZE)
  protected val writePacket = new DatagramPacket(new Array[Byte](PACKET_SIZE), 0, PACKET_SIZE)
  protected val timeOut = 1000
  protected val chunkSize = 100000

  private val random = ThreadLocalRandom.current()

  /**
    * Start the checking loop.
    */
  while (true) {
    socket.receive(readPacket)
    val destAdd = readPacket.getAddress
    val destPort = readPacket.getPort
    val extracted = Constants.seqAndPayload(readPacket)

    val seq = extracted._1
    if (seq == Flags.END_OF_TRANSFER.identifier) {
      sendAck(Flags.END_OF_TRANSFER.identifier, destAdd, destPort)
    } else {
      val wSize = Math.abs(extracted._1)
      val fileName = Constants.bytesToString(extracted._2)
      sendAck(Flags.INFO_NAME.identifier, destAdd, destPort)
      socket.setSoTimeout(timeOut)
      processFileTransfer(new Scribe(fileName), destAdd, destPort, wSize)
      socket.setSoTimeout(0)
    }
  }

  /**
    * Method to transform a packet into desired data form.
    *
    * @param packet The packet.
    * @return A tuple containing the sequence number and the payload.
    */
  protected def fromPacket(packet: DatagramPacket): (Int, Array[Byte])

  /**
    * Method called to process a file transfer.
    *
    * @param scribe     The scribe master for writing this file.
    * @param destAdd    The address origin of the file.
    * @param destPort   The port origin of the file.
    * @param windowSize The size of the window.
    */
  protected def processFileTransfer(scribe: Scribe, destAdd: InetAddress, destPort: Int, windowSize: Int): Unit

  /**
    * Method to send an ack packet to a destination.
    *
    * @param ack      The ack number.
    * @param destAdd  The destination address for the packet.
    * @param destPort The destination port for the packet.
    */
  protected def sendAck(ack: Int, destAdd: InetAddress, destPort: Int): Unit = {
    // socket.send(new DatagramPacket(Constants.intToByteArray(ack), 0, Constants.INT_BYTES, destAdd, destPort))
    writePacket.setAddress(destAdd)
    writePacket.setPort(destPort)
    val buffer = ByteBuffer.allocate(Constants.PACKET_SIZE)
    buffer.putInt(ack)
    writePacket.setData(buffer.array())
    socket.send(writePacket)
  }

  /**
    * Method to determine if a drop should be simulated.
    *
    * @return True to simulate a drop.
    */
  protected final def drop: Boolean = !simDrops && (random.nextInt(101) <= 1)

}
