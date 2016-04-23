package com.jeff.megaupload.client

import java.io.FileInputStream
import java.net.{DatagramPacket, DatagramSocket, InetAddress, SocketTimeoutException}

import com.jeff.megaupload.constant.Constants.{PACKET_SIZE, PAYLOAD_SIZE}
import com.jeff.megaupload.constant.{Constants, Flags, PacketMan}

import scala.collection.mutable.{Map => MutMap}


class Client {

  private val socket = new DatagramSocket()
  private val readPacket = new DatagramPacket(new Array[Byte](PACKET_SIZE), 0, PACKET_SIZE)

  private val PACKETS_IN_CHUNKS = 100000

  private val buffer = new Array[Byte](PAYLOAD_SIZE)

  def uploadFile(fileName: String, path: String, windowSize: Int, destAdd: InetAddress, destPort: Int): Unit = {
    initTransfer(fileName, windowSize, destAdd, destPort)
    processTransfer(new FileInputStream(path), windowSize, destAdd, destPort)
    end(destAdd, destPort)
    socket.close()
  }

  private def initTransfer(fileName: String, windowSize: Int, destAddress: InetAddress, destPort: Int): Unit = {
    val prev = socket.getSoTimeout
    socket.setSoTimeout(Constants.TIME_OUT)
    val initPacket = PacketMan.makePacket(PacketMan.makeInitPayload(fileName, windowSize), destAddress, destPort)
    var acknowledged = false

    while (!acknowledged) {
      socket.send(initPacket)
      try {
        socket.receive(readPacket)
        acknowledged = true
      } catch {
        case s: SocketTimeoutException =>
        case t: Throwable => throw t
      }
    }
    socket.setSoTimeout(prev)
  }


  private def processTransfer(stream: FileInputStream, windowSize: Int, destAddress: InetAddress, destPort: Int): Unit = {
    var seqNumber = 1
    var done = false

    while (!done) {
      val min = seqNumber
      val builder = MutMap[Int, Array[Byte]]()

      for (i <- 0 until PACKETS_IN_CHUNKS if !done) {
        val readCount = stream.read(buffer)
        readCount match {
          case -1 =>
            done = true
          case _ =>
            val out = buffer.slice(0, readCount)
            builder += seqNumber -> out
            seqNumber += 1
        }
      }

      val max = seqNumber - 1
      sendData(builder, windowSize, destAddress, destPort, min, max)
    }
  }

  private def sendData(packets: MutMap[Int, Array[Byte]], windowSize: Int,
                       destAddress: InetAddress, destPort: Int, min: Int, max: Int): Unit = {
    var counter = min
    while (counter <= max) {
      val localMax = Math.min(counter + windowSize, max)

      while (counter <= localMax) {
        socket.send(PacketMan.makePacket(PacketMan.makePayload(counter,
          packets.get(counter).get), destAddress, destPort))
        counter += 1
      }
      socket.receive(readPacket)

      var positive = false
      var highest = 1
      while (!positive) {
        socket.receive(readPacket)
        val seq = PacketMan.seqAndRawData(readPacket)._1
        if (seq >= 0) {
          positive = true
          highest = seq
        }
      }

      counter = highest
      println(s"COUNTER:$counter")
    }
  }

  private def end(destAddress: InetAddress, destPort: Int): Unit = {
    val prev = socket.getSoTimeout
    socket.setSoTimeout(Constants.TIME_OUT)
    val endPacket = PacketMan.makePacket(PacketMan.makeSeqPayload(Flags.END.id), destAddress, destPort)
    var acknowledged = false

    while (!acknowledged) {
      socket.send(endPacket)
      try {
        socket.receive(readPacket)
        acknowledged = true
      } catch {
        case s: SocketTimeoutException =>
        case t: Throwable => throw t
      }
    }
    socket.setSoTimeout(prev)
  }

}
