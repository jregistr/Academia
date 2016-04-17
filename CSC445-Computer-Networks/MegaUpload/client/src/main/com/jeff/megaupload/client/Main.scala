package com.jeff.megaupload.client

import java.io.{FileOutputStream, PrintWriter}
import java.nio.file.{Files, Paths}
import java.util.concurrent.ThreadLocalRandom


object Main {

  private val fileName = "./data.datafile"
  private val writeChunk = 10000

  def main(args: Array[String]) {

    createFile(100000000)

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
