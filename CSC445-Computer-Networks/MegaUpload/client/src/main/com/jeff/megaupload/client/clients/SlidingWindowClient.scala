package com.jeff.megaupload.client.clients

import java.io.FileInputStream
import java.net.{DatagramPacket, InetAddress, SocketTimeoutException}
import java.nio.ByteBuffer

import com.jeff.megaupload.constant.{Constants, Flags}

/**
  * The sliding window implementation of a transfer client.
  *
  * @param localAddress The local address for this client.
  * @param localPort    The local port for this client.
  */
class SlidingWindowClient(localAddress: String, localPort: Int) extends Client(localAddress, localPort) {

  override protected def processFileTransfer(stream: FileInputStream, inetAddress: InetAddress, destPort: Int): Unit = {
    var seqNumber = 1
    var done = false
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
    var counter = min
    while (counter <= max) {
      val localMax = Math.min(counter + windowSize, max)

      while (counter <= localMax) {
        socket.send(Constants.makePacket(counter, packets.get(counter).get, destAddress, destPort))
        counter += 1
      }
      val resp = getHighest(destAddress, destPort)
      counter = resp match {
        case 0 => 1
        case _ => resp
      }
    }
  }

  private def getHighest(destAddress: InetAddress, destPort: Int): Int = {
    var highest: Option[Int] = None
    while (highest.isEmpty) {
      try {
        socket.receive(readPacket)
        val extracted = Constants.seqAndPayload(readPacket)
        val seq = extracted._1
        if (seq >= 0) {
          highest = Some(seq)
        } else {
          throw new IllegalArgumentException
        }
      } catch {
        case s: SocketTimeoutException =>
          socket.send(Constants.makePacket(Flags.RESEND_HIGHEST.identifier, Constants.intToByteArray(0), destAddress, destPort))
        case t: Throwable => throw t
      }
    }
    highest.get
  }

}
