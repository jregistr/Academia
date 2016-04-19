package com.jeff.megaupload.server.servers

import java.io.ByteArrayOutputStream
import java.net.{DatagramPacket, InetAddress, SocketTimeoutException}

import akka.actor.ActorRef
import com.jeff.megaupload.constant.Flags
import com.jeff.megaupload.server.util.scribe.Write

/**
  * Sliding window implementation of a transfer server.
  *
  * @param port         The port this server runs on.
  * @param localAddress The local address of this server.
  * @param simDrops     True to simulate dropping 10% packets.
  */
class SlidingWindow(port: Int, localAddress: String, simDrops: Boolean) extends Server(port, localAddress, simDrops) {

  /**
    * Helper class represent a window item.
    *
    * @param ack  The ack number.
    * @param data The payload data.
    */
  class WindowItem(var ack: Int, var data: Array[Byte])

  private val windowSize = 500

  /**
    * Method called to process a file transfer.
    *
    * @param scribe   The scribe actor to write the data to file.
    * @param destAdd  The address origin of the file.
    * @param destPort The port origin of the file.
    */
  override protected def processFileTransfer(scribe: ActorRef, destAdd: InetAddress, destPort: Int): Unit = {
    var done = false
    while (!done) {
      val next = getWindow(destAdd, destPort)
      done = next._1
      val data = next._2
      if (data.length > 0) {
        scribe ! Write(data)
      }
    }
  }

  /**
    * Method called to complete the transfer of a full window's worth of data.
    *
    * @param destAdd  The source address for the transfer.
    * @param destPort The source port for the transfer.
    * @return The data transferred during said window.
    */
  private def getWindow(destAdd: InetAddress, destPort: Int): (Boolean, Array[Byte]) = {
    var highest = -1
    var transferDone = false

    val window = new Array[Array[Byte]](windowSize)
    val output = new ByteArrayOutputStream

    while (highest < windowSize - 1 && !transferDone) {
      try {
        socket.receive(readPacket)
        val extracted = fromPacket(readPacket)
        val seq = extracted._1
        seq match {
          case Flags.RESEND_HIGHEST.identifier =>
            sendHighestAck(highest, destAdd, destPort)
          case Flags.END_OF_TRANSFER.identifier =>
            transferDone = true
          case _ =>
            if (seq > highest && seq < windowSize) {
              window(seq) = extracted._2
            } else {
              throw new IllegalStateException(s"Received wrong SEQ, " +
                s"program might need re-evaluation.[Highest:$highest, SEQ:$seq]")
            }
        }
      } catch {
        case s: SocketTimeoutException =>
          highest = slideWindow(window, output, highest)
          sendHighestAck(highest, destAdd, destPort)
        case t: Throwable => throw t
      }
    }
    (transferDone, output.toByteArray)
  }

  /**
    * Method called to send the highest ack to the source.
    *
    * @param highest  The highest ack.
    * @param destAdd  The source address for the transfer.
    * @param destPort The source port for the transfer.
    */
  private def sendHighestAck(highest: Int, destAdd: InetAddress, destPort: Int): Unit = {
    sendAck(highest match {
      case -1 => Flags.NO_PACKET_RECEIVED.identifier
      case _ => highest
    }, destAdd, destPort)
  }

  /**
    * Method to determined highest received seq number. Also pushes data from given position to highest seq onto the byte buffer.
    *
    * @param window      The window array.
    * @param output      The output buffer.
    * @param lastHighest The last highest ack.
    * @return The new highest position.
    */
  private def slideWindow(window: Array[Array[Byte]], output: ByteArrayOutputStream, lastHighest: Int): Int = {
    val start = lastHighest + 1
    var current = window(start)
    var highest = lastHighest

    for (i <- start until window.length if current != null) {
      current = window(i)
      if (current != null) {
        output.write(current)
        highest = i
      }
    }
    highest
  }

  override protected def fromPacket(packet: DatagramPacket): (Int, Array[Byte]) = seqAndPayload(packet)

}
