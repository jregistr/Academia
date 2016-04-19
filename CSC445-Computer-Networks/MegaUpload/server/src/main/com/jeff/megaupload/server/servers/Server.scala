package com.jeff.megaupload.server.servers

import java.io.FileOutputStream
import java.net.{DatagramPacket, DatagramSocket, InetAddress, SocketTimeoutException}
import java.util.concurrent.ThreadLocalRandom

import akka.actor.{ActorRef, ActorSystem}
import com.jeff.megaupload.constant.Constants.PACKET_SIZE
import com.jeff.megaupload.constant.{Constants, Flags}
import com.jeff.megaupload.server.util.scribe.Scribe

/**
  * Class to represent an abstract server.
  *
  * @param port         The port this server runs on.
  * @param localAddress The local address of this server.
  * @param simDrops     True to simulate dropping 10% packets.
  */
abstract class Server(port: Int, localAddress: String, val simDrops: Boolean) {

  protected val socket = new DatagramSocket(port, InetAddress.getLocalHost)
  protected val readPacket = new DatagramPacket(new Array[Byte](PACKET_SIZE), 0, PACKET_SIZE)
  protected val writePacket = new DatagramPacket(new Array[Byte](PACKET_SIZE), 0, PACKET_SIZE)
  protected val timeOut = 1000

  private val system = ActorSystem("Scribes-Guild")

  private val random = ThreadLocalRandom.current()

  {
    /*val scribe = system.actorOf(Scribe.props(fileName))
      scribe ! Write(List(Array(1.toByte, 2.toByte)))
    scribe ! "Cool Stuff"
    scribe ! Finish*/
  }

  /**
    * Start the checking loop.
    */
  while (true) {
    socket.receive(readPacket)
//    val data = seqAndPayload(readPacket)._2
 //   val maxAck = Constants.byteArrayToInt(data)
    val destAdd = readPacket.getAddress
    val destPort = readPacket.getPort
    val fileName = acquireFileName(destAdd, destPort)
    val scribe = system.actorOf(Scribe.props(fileName))
    socket.setSoTimeout(timeOut)
    processFileTransfer(scribe, destAdd, destPort)
    socket.setSoTimeout(0)
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
    * @param scribe   The scribe actor to write the data to file.
    * @param destAdd  The address origin of the file.
    * @param destPort The port origin of the file.
    */
  protected def processFileTransfer(scribe: ActorRef, destAdd: InetAddress, destPort: Int): Unit

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
    * Method to gather the file name from the client.
    *
    * @param destAdd  The destination address.
    * @param destPort The destination port.
    * @return The file name.
    */
  protected final def acquireFileName(destAdd: InetAddress, destPort: Int): String = {
    var found = false
    var fileName: Option[String] = None
    sendAck(Flags.RESEND_NAME.identifier, destAdd, destPort)
    while (!found) {
      try {
        socket.receive(readPacket)
        val extracted = seqAndPayload(readPacket)
        val seq = extracted._1
        seq match {
          case Flags.INFO_NAME.identifier =>
            fileName = Some(Constants.bytesToString(extracted._2))
            found = true
          case _ => throw new IllegalStateException(s"Expected packet with name info but found Identifier:$seq")
        }
      } catch {
        case s: SocketTimeoutException =>
          sendAck(Flags.RESEND_NAME.identifier, destAdd, destPort)
        case t: Throwable => throw t
      }
    }
    fileName.get
  }

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
