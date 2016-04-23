package com.jeff.megaupload.constant

import java.net.{DatagramPacket, InetAddress}
import java.nio.ByteBuffer

import com.jeff.megaupload.constant.Constants._

object PacketMan {

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
    * Method to get the sequence number and the raw unpacked data.
    *
    * @param packet The packet.
    * @return The seq and raw data.
    */
  def seqAndRawData(packet: DatagramPacket): (Int, Array[Byte]) = {
    val raw = packet.getData
    val seq = byteArrayToInt(raw.slice(0, Constants.INT_BYTES))
    val rawData = raw.slice(INT_BYTES, raw.length)
    (seq, rawData)
  }

  /**
    * Method to unpack a data packet.
    *
    * @param raw The raw data to operate on.
    * @return The data.
    */
  def dePackRegular(raw: Array[Byte]): (Array[Byte]) = {
    val count = byteArrayToInt(raw.slice(0, INT_BYTES))
    raw.slice(INT_BYTES, INT_BYTES + count)
  }

  /**
    * Method to unpack an init packet.
    *
    * @param raw The raw data to operate on.
    * @return A tuple of the window size and file name.
    */
  def dePackInit(raw: Array[Byte]): (Int, String) = {
    val windowSize = byteArrayToInt(raw.slice(0, INT_BYTES))
    val sizeStart = INT_BYTES
    val sizeEnd = INT_BYTES * 2
    val sizeToRead = byteArrayToInt(raw.slice(sizeStart, sizeEnd))
    val name = bytesToString(raw.slice(sizeEnd, sizeEnd + sizeToRead))
    (windowSize, name)
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
    buffer.put(intToByteArray(seq))
    buffer.put(intToByteArray(bytes.length))
    buffer.put(bytes)
    buffer.array()
  }

  /**
    * Method to make initializing payload.
    *
    * @param fileName   The name of the file.
    * @param windowSize The size of the window.
    * @return The payload.
    */
  def makeInitPayload(fileName: String, windowSize: Int): Array[Byte] = {
    val nameAsBytes = fileName.getBytes("UTF-8")
    if (nameAsBytes.length > (Constants.PAYLOAD_SIZE - INT_BYTES))
      throw new IllegalArgumentException("Size of payload larger than allowed.")

    val buffer = ByteBuffer.allocate(Constants.PACKET_SIZE)
    buffer.put(intToByteArray(Flags.INIT.id))
    buffer.put(intToByteArray(windowSize))
    buffer.put(intToByteArray(nameAsBytes.length))
    buffer.put(nameAsBytes)
    buffer.array()
  }

  /**
    * Method to make simple sequence number packet.
    *
    * @param seq The sequence number.
    * @return The payload.
    */
  def makeSeqPayload(seq: Int): Array[Byte] = {
    val buffer = ByteBuffer.allocate(Constants.PACKET_SIZE)
    buffer.putInt(seq)
    buffer.putInt(0)
    buffer.array()
  }

  /**
    * Method to create and configure a packet to send.
    *
    * @param payload     The payload.
    * @param destAddress The destination address of this packet.
    * @param destPort    The destination port of this packet.
    * @return The packet.
    */
  def makePacket(payload: Array[Byte], destAddress: InetAddress, destPort: Int): DatagramPacket = {
    val packet = new DatagramPacket(payload, 0, payload.length)
    packet.setData(payload)
    packet.setAddress(destAddress)
    packet.setPort(destPort)
    packet
  }

}
