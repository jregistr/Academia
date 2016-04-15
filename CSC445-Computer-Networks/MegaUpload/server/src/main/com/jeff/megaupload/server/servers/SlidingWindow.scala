package com.jeff.megaupload.server.servers

import java.net.{DatagramPacket, InetAddress}


class SlidingWindow(port: Int, localAddress: String, simDrops: Boolean) extends Server(port, localAddress, simDrops) {

  while (true) {
    
  }

  private def processFileTransfer(lastAck: Int, destAdd: InetAddress, destPort: Int): Unit = {

  }

  override protected def fromPacket(packet: DatagramPacket): (Int, Array[Byte]) = seqAndPayload(packet)

}
