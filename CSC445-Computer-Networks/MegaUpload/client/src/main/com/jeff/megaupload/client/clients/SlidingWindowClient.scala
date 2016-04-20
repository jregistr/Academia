package com.jeff.megaupload.client.clients

import java.io.FileInputStream
import java.net.{DatagramPacket, InetAddress, SocketTimeoutException}
import java.nio.ByteBuffer

import com.jeff.megaupload.constant.Constants._
import com.jeff.megaupload.constant.{Constants, Flags}

/**
  * The sliding window implementation of a transfer client.
  *
  * @param localAddress The local address for this client.
  * @param localPort    The local port for this client.
  */
class SlidingWindowClient(localAddress: String, localPort: Int) extends Client(localAddress, localPort) {

  /**
    * Method to call to make a packet from the given data.
    *
    * @param seq         The sequence number.
    * @param bytes       The payload data.
    * @param destAddress The destination address.
    * @param destPort    The destination port.
    * @return The packet.
    */
  override protected def asPacket(seq: Int, bytes: Array[Byte], destAddress: InetAddress, destPort: Int): DatagramPacket = {

    if (bytes.length > Constants.PAYLOAD_SIZE)
      throw new IllegalArgumentException("Size of payload larger than allowed.")

    val buffer = ByteBuffer.allocate(Constants.PACKET_SIZE)
    buffer.put(Constants.intToByteArray(seq))
    buffer.put(Constants.intToByteArray(bytes.length))
    buffer.put(bytes)

    val payload = buffer.array()
    val packet = writePacket
    packet.setData(payload)
    packet.setAddress(destAddress)
    packet.setPort(destPort)

    packet
  }

  /**
    * Method to call to send a chunk of data to the server.
    *
    * @param packets     The packet data.
    * @param destAddress The destination address.
    * @param destPort    The source Address.
    * @param min         The minimum seq number.
    * @param max         The maximum seq number.
    */
  private def send(packets: Map[Int, Array[Byte]], destAddress: InetAddress, destPort: Int, min: Int, max: Int): Unit = {

  }

  private def getHighest(destAddress: InetAddress, destPort: Int): Int = {
    var highest:Option[Int] = None
    while (highest.isEmpty){
      socket.receive(readPacket)
      val extracted =
    }
    highest.get
  }

  /**
    * Transfers a file to the given destination.
    *
    * @param path        The path to the file.
    * @param destAddress The destination address.
    * @param destPort    The destination port.
    */
  final def uploadFile(path: String, destAddress: String, destPort: Int): Unit = {
    val inetAddress = InetAddress.getByName(destAddress)
    val last = path.lastIndexOf("/")
    val fn = last match {
      case -1 => path
      case _ => path.substring(last + 1, path.length)
    }

    var seqNumber = 1

    val startPacket = asPacket(-windowSize, fn.getBytes("UTF-8"), inetAddress, destPort)
    socket.send(startPacket)
    var acked = false
    while (!acked) {
      try {
        socket.receive(readPacket)
        val extracted = Constants.seqAndPayload(readPacket)
        extracted._1 match {
          case Flags.INFO_NAME.identifier => acked = true
          case _ =>
            acked = true
        }
      } catch {
        case s: SocketTimeoutException =>
          socket.send(startPacket)
        case t: Throwable => throw t
      }
    }

    val stream = new FileInputStream(path)
    var done = false
    val buffer = new Array[Byte](PAYLOAD_SIZE)

    while (!done) {
      val min = seqNumber
      val packets = (for (i <- 0 until PACKETS_IN_CHUNKS if !done) yield {
        val readCount = stream.read(buffer)
        readCount match {
          case -1 =>
            done = true
            None
          case _ =>
            val out = Some(seqNumber -> buffer.slice(0, readCount))
            seqNumber += 1
            out
        }
      }).filter(_.isDefined).map(_.get).toMap
      val max = seqNumber - 1

      send(packets, inetAddress, destPort, min, max)
    }
  }

}
