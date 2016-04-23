package com.jeff.megaupload.server

import java.io.ByteArrayOutputStream
import java.net.{DatagramPacket, DatagramSocket, InetAddress, SocketTimeoutException}
import java.util.concurrent.ThreadLocalRandom

import com.jeff.megaupload.constant.Constants.PACKET_SIZE
import com.jeff.megaupload.constant.{Constants, Flags, PacketMan}
import com.jeff.megaupload.server.util.scribe.Scribe

import scala.collection.mutable.{Map => MutMap}


class Server(port: Int, simDrops: Boolean) {

  private val socket = new DatagramSocket(port)
  private val readPacket = new DatagramPacket(new Array[Byte](PACKET_SIZE), 0, PACKET_SIZE)
  private val chunkSize = 100000

  private val random = ThreadLocalRandom.current()

  while (true) {
    println("I am starting")
    socket.receive(readPacket)
    val destAdd = readPacket.getAddress
    val destPort = readPacket.getPort
    val extracted = PacketMan.seqAndRawData(readPacket)
    val seq = extracted._1
    val raw = extracted._2

    seq match {
      case Flags.END.id =>
        sendAck(Flags.END.id, destAdd, destPort)
      case Flags.INIT.id =>
        val processed = PacketMan.dePackInit(raw)
        val windowSize = processed._1
        val fileName = processed._2
        println(fileName)
        sendAck(Flags.INIT.id, destAdd, destPort)
        socket.setSoTimeout(Constants.TIME_OUT)
        val scribe = new Scribe(fileName)
        processFileTransfer(scribe, destAdd, destPort, windowSize)
        socket.setSoTimeout(0)
      case _ => throw new IllegalArgumentException("Unexpected sequence number")
    }

  }

  private def processFileTransfer(scribe: Scribe, destAdd: InetAddress, destPort: Int, windowSize: Int): Unit = {
    var done = false
    var highestSeq = 1
    var noneReceived = true
    var maxSeq = windowSize
    val window = MutMap[Int, Array[Byte]]()
    var stream = new ByteArrayOutputStream

    while (!done) {
      var count = 0
      while (count < windowSize && !done) {
        try {
          socket.receive(readPacket)
          val extracted = PacketMan.seqAndRawData(readPacket)
          val seq = extracted._1
          val rawData = extracted._2

          seq match {
            case Flags.INIT.id =>
              sendAck(Flags.INIT.id, destAdd, destPort)
            case Flags.END.id =>
              done = true
              sendAck(Flags.END.id, destAdd, destPort)
            case _ =>
              window += seq -> PacketMan.dePackRegular(rawData)
              count += 1
              noneReceived = false
          }

        } catch {
          case s: SocketTimeoutException =>
            var checkDone = false
            while (!checkDone) {
              if (highestSeq > maxSeq) {
                checkDone = true
                maxSeq = highestSeq + windowSize
                sendAck(highestSeq, destAdd, destPort)
              } else {
                if (noneReceived) {
                  sendAck(1, destAdd, destPort)
                  checkDone = true
                } else {
                  val get = window.get(highestSeq)
                  get match {
                    case None =>
                      checkDone = true
                      maxSeq = highestSeq + windowSize
                      sendAck(highestSeq, destAdd, destPort)
                    case Some(value) =>
                      highestSeq += 1
                      stream.write(value)
                      if (stream.size() >= chunkSize) {
                        scribe.write(stream.toByteArray)
                        stream = new ByteArrayOutputStream()
                      }
                  }
                }
              }
            }
          case t: Throwable => throw t
        }
      }
    }

    if (stream.size() > 0) {
      scribe.write(stream.toByteArray)
    }
    scribe.finish()
  }

  private def sendAck(seq: Int, destAdd: InetAddress, destPort: Int) =
    socket.send(PacketMan.makePacket(PacketMan.makeSeqPayload(seq), destAdd, destPort))

  private def drop = simDrops && random.nextInt(101) <= 1

}
