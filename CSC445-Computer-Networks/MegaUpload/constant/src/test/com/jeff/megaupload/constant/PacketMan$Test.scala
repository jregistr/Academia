package com.jeff.megaupload.constant

import java.net.InetAddress
import java.nio.ByteBuffer

import org.junit.Test
import org.hamcrest.CoreMatchers._
import org.junit.Assert.assertThat

import scala.util.Random

class PacketMan$Test {

  private val random = new Random()

  @Test
  def intToByteArrayTest = {
    val temp = random.nextInt
    val toBytes = PacketMan.intToByteArray(temp)

    val buffer = ByteBuffer.allocate(Integer.BYTES)
    buffer.putInt(temp)
    assertThat(buffer.array(), is(equalTo(toBytes)))
  }

  @Test
  def testRegularPacket = {
    val seq = Math.abs(random.nextInt())
    val data = Random.nextString(10)
    val inet = InetAddress.getByName("localhost")
    val port = 7000

    val packet = PacketMan.makePacket(PacketMan.makePayload(seq, data.getBytes("UTF-8")), inet, port)
    assertThat(packet.getAddress.getHostName, is(equalTo(inet.getHostName)))

    assertThat(Constants.PACKET_SIZE, is(equalTo(packet.getData.length)))

    val rawExtact = PacketMan.seqAndRawData(packet)
    val outSeq = rawExtact._1
    val outRawData = rawExtact._2

    assertThat(outSeq, is(equalTo(seq)))

    val outData = PacketMan.dePackRegular(outRawData)
    val outDataToString = PacketMan.bytesToString(outData)

    assertThat(outDataToString, is(equalTo(data)))

  }

  @Test
  def testInitPacket = {
    val windowSize = Math.abs(Random.nextInt())
    val fileName = Random.nextString(10)
    val inet = InetAddress.getByName("localhost")
    val port = 7000

    val packet = PacketMan.makePacket(PacketMan.makeInitPayload(fileName, windowSize), inet, port)
    assertThat(packet.getAddress.getHostName, is(equalTo(inet.getHostName)))
    assertThat(Constants.PACKET_SIZE, is(equalTo(packet.getData.length)))

    val rawExtract = PacketMan.seqAndRawData(packet)
    val outSeq = rawExtract._1
    val outRawData = rawExtract._2

    assertThat(outSeq, is(equalTo(Flags.INIT.id)))

    val processedOut = PacketMan.dePackInit(outRawData)

    val processedWS = processedOut._1
    val procssedFN = processedOut._2

    assertThat(processedWS, is(equalTo(windowSize)))
    assertThat(procssedFN, is(equalTo(fileName)))

  }

}
