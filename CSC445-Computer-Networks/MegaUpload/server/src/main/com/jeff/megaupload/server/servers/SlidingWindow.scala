package com.jeff.megaupload.server.servers

import java.io.ByteArrayOutputStream
import java.net.{DatagramPacket, InetAddress, SocketTimeoutException}

import com.jeff.megaupload.constant.{Constants, Flags}
import com.jeff.megaupload.server.util.scribe.Scribe

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
    * @param scribe     The scribe master for writing this file.
    * @param destAdd    The address origin of the file.
    * @param destPort   The port origin of the file.
    * @param windowSize The size of the window.
    */
  override protected def processFileTransfer(scribe: Scribe, destAdd: InetAddress, destPort: Int, windowSize: Int): Unit = {
    var done = false
    var highestSeq = 1
    var noneReceived = true
    var maxSeq = windowSize

    val window = MutMap[Int, Array[Byte]]()
    var stream = new ByteArrayOutputStream

    def timeOutOperation(): Unit = {
      var checkDone = false
      while (!checkDone) {
        if (highestSeq > maxSeq) {
          checkDone = true
          maxSeq = highestSeq + windowSize
          sendHighestAck(highestSeq, destAdd, destPort)
        } else {
          if (noneReceived) {
            sendHighestAck(Flags.NO_PACKET_RECEIVED.identifier, destAdd, destPort)
          } else {
            val get = window.get(highestSeq)
            get match {
              case None =>
                checkDone = true
                maxSeq = highestSeq + windowSize
                sendHighestAck(highestSeq, destAdd, destPort)
              case Some(value) =>
                highestSeq += 1
                stream.write(value)
                if (stream.size() >= chunkSize) {
                  scribe.write(stream.toByteArray)
                  stream = new ByteArrayOutputStream()
                }
            }
          }
        }
      }
    }

    while (!done) {
      var count = 0
      while (count < windowSize && !done) {
        try {
          socket.receive(readPacket)
          val extracted = Constants.seqAndPayload(readPacket)
          val seq = extracted._1
          val data = extracted._2
          seq match {
            case Flags.RESEND_HIGHEST.identifier =>
              sendHighestAck(highestSeq, destAdd, destPort)
            case Flags.END_OF_TRANSFER.identifier =>
              done = true
              sendHighestAck(Flags.END_OF_TRANSFER.identifier, destAdd, destPort)
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

    if (stream.size() > 0) {
      scribe.write(stream.toByteArray)
    }

    scribe.finish()
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

  override protected def fromPacket(packet: DatagramPacket): (Int, Array[Byte]) = Constants.seqAndPayload(packet)

}
