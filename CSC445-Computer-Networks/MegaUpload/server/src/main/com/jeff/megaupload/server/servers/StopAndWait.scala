package com.jeff.megaupload.server.servers

import java.io.ByteArrayOutputStream
import java.net.{DatagramPacket, InetAddress}

import com.jeff.megaupload.constant.{Constants, Flags}
import com.jeff.megaupload.server.util.scribe.Scribe


class StopAndWait(port: Int, localAddress: String, simDrops: Boolean) extends Server(port, localAddress, simDrops) {

  /**
    * Method to transform a packet into desired data form.
    *
    * @param packet The packet.
    * @return A tuple containing the sequence number and the payload.
    */
  override protected def fromPacket(packet: DatagramPacket): (Int, Array[Byte]) = Constants.seqAndPayload(packet)

  /**
    * Method called to process a file transfer.
    *
    * @param scribe     The scribe master for writing this file.
    * @param destAdd    The address origin of the file.
    * @param destPort   The port origin of the file.
    * @param windowSize The size of the window.
    */
  override protected def processFileTransfer(scribe: Scribe, destAdd: InetAddress, destPort: Int, windowSize: Int): Unit = {
    var buffer = new ByteArrayOutputStream
    socket.setSoTimeout(0)
    var done = false

    while (!done) {
      socket.receive(readPacket)
      val extracted = Constants.seqAndPayload(readPacket)
      val seq = extracted._1
      val data = extracted._2
      seq match {
        case Flags.END_OF_TRANSFER.identifier => done = true
        case _ =>
          buffer.write(data)
          if (buffer.size() >= chunkSize) {
            scribe.write(buffer.toByteArray)
            buffer = new ByteArrayOutputStream()
          }
          sendAck(seq, destAdd, destPort)
      }
    }

    if (buffer.size() > 0) {
      scribe.write(buffer.toByteArray)
    }

    scribe.finish()
  }

}
