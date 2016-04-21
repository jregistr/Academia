package com.jeff.megaupload.client

import java.io.FileOutputStream
import java.nio.file.{Files, Paths}
import java.util.concurrent.ThreadLocalRandom

import com.jeff.megaupload.client.clients.SlidingWindowClient

object Main {

  private val fileName = "./data.datafile"
  private val writeChunk = 10000

  def main(args: Array[String]) {
   /* val in = getClass.getClassLoader.getResource("TCPThroughClient.scala").getPath
    new SlidingWindowClient("localhost", 7001).uploadFile(in, "localhost", 7000)*/
    createFile(400000000)
  }

  private def createFile(size: Int): Unit = {
    Files.deleteIfExists(Paths.get(fileName))
    val stream = new FileOutputStream(fileName)
    val random = ThreadLocalRandom.current()

    var remain = size

    while (remain > 0) {
      val now = if (remain >= writeChunk) writeChunk else remain
      remain -= now
      val bytes = new Array[Byte](now)
      random.nextBytes(bytes)
      stream.write(bytes)
    }

    stream.flush()
    stream.close()
  }

}
