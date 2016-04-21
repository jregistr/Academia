package com.jeff.megaupload.client.clients

import java.io.FileInputStream
import java.net.{InetAddress, SocketTimeoutException}

import com.jeff.megaupload.constant.Constants


class StopAndWaitClient(localAddress: String, localPort: Int) extends Client(localAddress, localPort) {

  private val seq = 1

  /**
    * Method called to process the file transfer with the server.
    *
    * @param stream      The file stream to pull data from.
    * @param destAddress The destination address.
    * @param destPort    The destination port.
    */
  override protected def processFileTransfer(stream: FileInputStream, destAddress: InetAddress, destPort: Int): Unit = {
    var done = false
    while (!done) {
      val packets = (for (i <- 0 until PACKETS_IN_CHUNKS if !done) yield {
        val readCount = stream.read(buffer)
        readCount match {
          case -1 =>
            done = true
            None
          case _ =>
            Some(buffer.slice(0, readCount))
        }
      }).filter(_.isDefined).map(_.get).toArray

      send(packets, destAddress, destPort)
    }
  }

  /**
    * Sends data to server.
    *
    * @param packets     The collection of inner data.
    * @param destAddress The destination address.
    * @param destPort    The destination por.
    */
  private def send(packets: Array[Array[Byte]], destAddress: InetAddress, destPort: Int): Unit = {
    packets.foreach(data => {
      val packet = Constants.makePacket(seq, data, destAddress, destPort)
      socket.send(packet)
      try {
        socket.receive(readPacket)
      } catch {
        case s: SocketTimeoutException => socket.send(packet)
      }
    })
  }

}
