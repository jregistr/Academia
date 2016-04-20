package com.jeff.megaupload.constant

import java.net.DatagramPacket
import java.nio.ByteBuffer

import scala.collection.mutable.ArrayBuffer


object Constants {

  val INT_BYTES = 4

  val FLAG_RESEND_HIGHEST = -1


  val PACKET_SIZE = 1000
  val PAYLOAD_SIZE = PACKET_SIZE - (INT_BYTES * 2)

  def intToByteArray(value: Int): Array[Byte] = {
    val buf = new ArrayBuffer[Byte](INT_BYTES)
    for (i <- 0 until INT_BYTES) {
      buf += ((value >>> (INT_BYTES - i - 1 << 3)) & 0xFF).toByte
    }
    buf.toArray
  }

  def byteArrayToInt(value: Array[Byte]): Int = {
    ByteBuffer.wrap(value).getInt
  }

  def bytesToString(value: Array[Byte]): String = {
    new String(value, "UTF-8")
  }

  def seqAndPayload(packet: DatagramPacket): (Int, Array[Byte]) = {
    val raw = packet.getData
    val seq = Constants.byteArrayToInt(raw.slice(0, Constants.INT_BYTES))
    val countStartIndex = Constants.INT_BYTES
    val endCountIndex = countStartIndex + Constants.INT_BYTES
    val count = Constants.byteArrayToInt(raw.slice(countStartIndex, endCountIndex))
    val payLoad = raw.slice(endCountIndex, endCountIndex + count)
    (seq, payLoad)
  }

}
