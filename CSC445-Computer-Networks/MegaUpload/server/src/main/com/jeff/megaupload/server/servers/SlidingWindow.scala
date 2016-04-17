package com.jeff.megaupload.server.servers

import java.net.{DatagramPacket, InetAddress}

import com.jeff.megaupload.constant.Constants

import scala.collection.mutable.ListBuffer


class SlidingWindow(port: Int, localAddress: String, simDrops: Boolean) extends Server(port, localAddress, simDrops) {

  class WindowItem(var ack: Int, var data: Array[Byte])

  while (true) {
    socket.receive(readPacket)
    val data = seqAndPayload(readPacket)._2
    val maxAck = Constants.byteArrayToInt(data.slice(0, Constants.INT_BYTES))
    processFileTransfer(maxAck, readPacket.getAddress, readPacket.getPort)
  }

  private def processFileTransfer(lastAck: Int, destAdd: InetAddress, destPort: Int): Unit = {
    var done = false
    val fileData = new ListBuffer[Array[Byte]]()
    val window = createWindow(500)

    while (!done) {

    }
  }

  private def slideWindow(window: Array[Array[WindowItem]], out: ListBuffer[Array[WindowItem]], position: Int): Unit = {
    for (i <- 0 to position) {
      out += window(i)
      val op = i + position
      if (op < window.length) {
        window.update(i, window(op))
      }
    }
  }

  private def createWindow(size: Int): Array[WindowItem] = {
    val window = new Array[WindowItem](size)
    for (i <- 0 until size) {
      window.update(i, new WindowItem(0, null))
    }
    window
  }

  override protected def fromPacket(packet: DatagramPacket): (Int, Array[Byte]) = seqAndPayload(packet)

}
