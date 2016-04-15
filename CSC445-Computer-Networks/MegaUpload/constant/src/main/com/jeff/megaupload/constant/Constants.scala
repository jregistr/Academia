package com.jeff.megaupload.constant

import java.nio.ByteBuffer

import scala.collection.mutable.ArrayBuffer


object Constants {

  val INT_BYTES = 4

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

}
