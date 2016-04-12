package com.jeff.megaupload.client.clients

import java.io.FileInputStream
import java.net.{DatagramSocket, InetAddress}


abstract class Client(localAddress: String, localPort: Int) {

  private final val MAX_CHUNK_SIZE = 256000
  private final val PAYLOAD_SIZE = 1000

  protected val socket = new DatagramSocket(localPort, InetAddress.getByName(localAddress))

  final def uploadFile(path: String, destAddress: String, destPort: Int): Unit = {
    val stream = new FileInputStream(path)
    var lastReadSize = 0
    //when read returns -1, end of stream. while loop for until end with
    //inner while loop condition on made full chunk or end of file.
    //after each inner loop. chunk was made, so upload chunk.
    //outer loop keeps map for current chunk.
  }

  protected def asPacket(bytes: Array[Byte], destAddress: String, destPort: Int)

}
