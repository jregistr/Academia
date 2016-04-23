package com.jeff.megaupload.client

import java.io.FileOutputStream
import java.net.InetAddress
import java.nio.file.{Files, Paths}
import java.util.concurrent.ThreadLocalRandom

import scala.io.StdIn._

object Main {

  private val writeChunk = 10000

  def main(args: Array[String]) {

    readLine("File Transfer?(true) or Create File(false)???:").toBoolean match {
      case true =>
        val serverAdd = readLine("Enter server dns:")
        val serverPort = readLine("Enter server port:").toInt
        val inet6 = readLine("Use Inet 6?:").toBoolean
        val fileName = readLine("Enter File Name:")
        val filePath = readLine("Enter path to file:")
        val windowSize = readLine("Enter window size:").toInt
        new Client().uploadFile(fileName, filePath, windowSize, InetAddress.getByName(serverAdd), serverPort)
      case _ =>
        val fileName = readLine("Enter File Name:")
        val size = readLine("Enter File Size In Mega Bytes:").toInt * 1000000
        createFile(size, fileName)
    }

    /*val in = getClass.getClassLoader.getResource("data22.datafile").getPath
    new Client().uploadFile("data22.datafile", in,
      500, InetAddress.getByName("localhost"), 7000)*/
  }

  /**
    * Method to generate a file of a given size filled with random bytes.
    *
    * @param size The size of the file.
    */
  private def createFile(size: Int, fileName: String): Unit = {
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
