package com.jeff.megaupload.client.clients

import java.io.FileInputStream
import java.net.{DatagramPacket, DatagramSocket, InetAddress}
import com.jeff.megaupload.constant.Constants.PAYLOAD_SIZE

import scala.collection.mutable.ListBuffer


abstract class Client(localAddress: String, localPort: Int) {

  protected val socket = new DatagramSocket(localPort, InetAddress.getByName(localAddress))

  final def uploadFile(path: String, destAddress: String, destPort: Int): Unit = {
    val stream = new FileInputStream(path)
    var done = false
    val buffer = new Array[Byte](PAYLOAD_SIZE)
    val packetBuffer = new ListBuffer[DatagramPacket]()
    while (!done) {
      val readCount = stream.read(buffer)
      if (readCount != -1) {
        packetBuffer += asPacket(buffer.slice(0, readCount), destAddress, destPort)
      } else {
        done = true
      }
    }
    send(packetBuffer.toList, destAddress, destPort)
  }

  protected def asPacket(bytes: Array[Byte], destAddress: String, destPort: Int): DatagramPacket

  protected def send(packets: List[DatagramPacket], destAddress: String, destPort: Int): Unit

}
