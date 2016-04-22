package com.jeff.megaupload.constant

import java.net.{DatagramPacket, InetAddress}
import java.nio.ByteBuffer

/**
  * Class to hold constants and common functions between server and client.
  */
object Constants {

  val INT_BYTES = 4

  val FLAG_RESEND_HIGHEST = -1

  val PACKET_SIZE = 64000
  val PAYLOAD_SIZE = PACKET_SIZE - (INT_BYTES * 2)

  /**
    * Method to convert an Integer into a byte array.
    *
    * @param value The value to convert.
    * @return The output.
    */
  def intToByteArray(value: Int): Array[Byte] = {
    val buffer = ByteBuffer.allocate(Integer.BYTES)
    buffer.putInt(value)
    buffer.array()
  }

  /**
    * Converts a byte array into an Integer.
    *
    * @param value The byte array to convert.
    * @return The integer value.
    */
  def byteArrayToInt(value: Array[Byte]): Int = {
    ByteBuffer.wrap(value).getInt
  }

  /**
    * Converts a byte array into a UTF-8 String.
    *
    * @param value The value to convert.
    * @return The converted output.
    */
  def bytesToString(value: Array[Byte]): String = {
    new String(value, "UTF-8")
  }

  /**
    * Method to grab sequence number and payload from a packet.
    *
    * @param packet The packet to mine data from.
    * @return The values mined.
    */
  def seqAndPayload(packet: DatagramPacket): (Int, Array[Byte]) = {
    val raw = packet.getData
    val seq = Constants.byteArrayToInt(raw.slice(0, Constants.INT_BYTES))
    val countStartIndex = Constants.INT_BYTES
    val endCountIndex = countStartIndex + Constants.INT_BYTES
    val count = Constants.byteArrayToInt(raw.slice(countStartIndex, endCountIndex))
    val payLoad = raw.slice(endCountIndex, endCountIndex + count)
    (seq, payLoad)
  }

  /**
    * Method to create properly configured payload data for a packet.
    *
    * @param seq   The sequence number.
    * @param bytes The inner payload data.
    * @return The payload for a packet.
    */
  def makePayload(seq: Int, bytes: Array[Byte]): Array[Byte] = {
    if (bytes.length > Constants.PAYLOAD_SIZE)
      throw new IllegalArgumentException("Size of payload larger than allowed.")
    val buffer = ByteBuffer.allocate(Constants.PACKET_SIZE)
    buffer.put(Constants.intToByteArray(seq))
    buffer.put(Constants.intToByteArray(bytes.length))
    buffer.put(bytes)
    buffer.array()
  }

  /**
    * Method to create and configure a packet to send.
    *
    * @param seq         The sequence number.
    * @param bytes       The inner data to use to make the payload.
    * @param destAddress The destination address of this packet.
    * @param destPort    The destination port of this packet.
    * @return The packet.
    */
  def makePacket(seq: Int, bytes: Array[Byte], destAddress: InetAddress, destPort: Int): DatagramPacket = {
    val payload = makePayload(seq, bytes)
    val packet = new DatagramPacket(payload, 0, payload.length)
    packet.setData(payload)
    packet.setAddress(destAddress)
    packet.setPort(destPort)
    packet
  }

}
