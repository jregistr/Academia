package com.jeff.megaupload.server.servers

import java.io.FileOutputStream
import java.net.{DatagramPacket, DatagramSocket, InetAddress}
import java.nio.ByteBuffer
import java.util.concurrent.ThreadLocalRandom

import akka.actor.{ActorRef, ActorSystem}
import com.jeff.megaupload.constant.{Constants, Flags}
import com.jeff.megaupload.constant.Constants.PACKET_SIZE
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

  private val system = ActorSystem("Scribes-Guild")

  private val random = ThreadLocalRandom.current()

  /**
    * Start the checking loop.
    */
  while (true) {
    socket.receive(readPacket)
    val destAdd = readPacket.getAddress
    val destPort = readPacket.getPort
    val extracted = seqAndPayload(readPacket)

    val seq = extracted._1
    if (seq == Flags.END_OF_TRANSFER.identifier) {
      sendAck(Flags.END_OF_TRANSFER.identifier, destAdd, destPort)
    } else {
      val wSize = Math.abs(extracted._1)
      val fileName = Constants.bytesToString(extracted._2)
      sendAck(Flags.INFO_NAME.identifier, destAdd, destPort)
      val scribe = system.actorOf(Scribe.props(fileName))
      socket.setSoTimeout(timeOut)
//      println(s"SIZE:$wSize, FileName:$fileName")
      processFileTransfer(fileName, destAdd, destPort, wSize)
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
    * @param fileName    The scribe actor to write the data to file.
    * @param destAdd    The address origin of the file.
    * @param destPort   The port origin of the file.
    * @param windowSize The size of the window.
    */
  protected def processFileTransfer(fileName: String, destAdd: InetAddress, destPort: Int, windowSize: Int): Unit

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
    * Method to determine if a drop should be simulated.
    *
    * @return True to simulate a drop.
    */
  protected final def drop: Boolean = !simDrops && (random.nextInt(101) <= 1)

  /**
    * Method to write a file with the given name using the given binary data.
    *
    * @param fileName The name of the file.
    * @param data     The data for the file.
    */
  protected final def saveFile(fileName: String, data: List[Array[Byte]]): Unit = {
    val stream = new FileOutputStream(fileName)
    data.foreach(stream.write)
    stream.flush()
    stream.close()
  }

}
