package com.jeff.megaupload.server.servers

import java.io.ByteArrayOutputStream
import java.net.{DatagramPacket, InetAddress, SocketTimeoutException}

import akka.actor.ActorRef
import com.jeff.megaupload.constant.Flags

import scala.collection.mutable.{Map => MutMap}

/**
  * Sliding window implementation of a transfer server.
  *
  * @param port         The port this server runs on.
  * @param localAddress The local address of this server.
  * @param simDrops     True to simulate dropping 10% packets.
  */
class SlidingWindow(port: Int, localAddress: String, simDrops: Boolean) extends Server(port, localAddress, simDrops) {

  /**
    * Method called to process a file transfer.
    *
    * @param scribe     The scribe actor to write the data to file.
    * @param destAdd    The address origin of the file.
    * @param destPort   The port origin of the file.
    * @param windowSize The size of the window.
    */
  override protected def processFileTransfer(scribe: ActorRef, destAdd: InetAddress, destPort: Int, windowSize: Int): Unit = {
    var done = false
    var highestSeq = 1
    var noneReceived = true
    def maxSeq = highestSeq + windowSize

    val window = MutMap[Int, Array[Byte]]()
    var stream = new ByteArrayOutputStream

    def timeOutOperation(): Unit = {
      var checkDone = false
      val max = maxSeq
      while (!checkDone) {
        if (highestSeq > max) {
          checkDone = true
        } else {
          if (noneReceived) {
            sendHighestAck(Flags.NO_PACKET_RECEIVED.identifier, destAdd, destPort)
          } else {
            val get = window.get(highestSeq)
            get match {
              case None =>
                checkDone = true
                sendHighestAck(highestSeq, destAdd, destPort)
              case Some(value) =>
                stream.write(value)
                if (stream.size() >= chunkSize) {
                  scribe ! stream.toByteArray
                  stream = new ByteArrayOutputStream()
                }
            }
            highestSeq += 1
          }
        }
      }
    }

    while (!done) {
      var count = 0
      while (count < windowSize && !done) {
        try {
          socket.receive(readPacket)
          val extracted = seqAndPayload(readPacket)
          val seq = extracted._1
          val data = extracted._2

          seq match {
            case Flags.RESEND_HIGHEST.identifier =>
              sendHighestAck(highestSeq, destAdd, destPort)
            case Flags.END_OF_TRANSFER.identifier =>
              done = true
            case _ =>
              if (seq < Flags.SEND_WINDOW_SIZE.identifier) {
                sendAck(Flags.INFO_NAME.identifier, destAdd, destPort)
              } else {
                window += seq -> data
                count += 1
                noneReceived = false
              }
          }
        } catch {
          case s: SocketTimeoutException =>
            timeOutOperation()
          case t: Throwable => throw t
        }
      }
    }
  }

  /**
    * Method called to complete the transfer of a full window's worth of data.
    *
    * @param windowSize The size of the window to use.
    * @param destAdd    The source address for the transfer.
    * @param destPort   The source port for the transfer.
    * @return The data transferred during said window.
    */
  private def getWindow(windowSize: Int, destAdd: InetAddress, destPort: Int): (Boolean, Array[Byte]) = {
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
