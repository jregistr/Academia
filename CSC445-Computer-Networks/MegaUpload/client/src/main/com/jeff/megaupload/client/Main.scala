package com.jeff.megaupload.client

import java.io.FileOutputStream
import java.net.InetAddress
import java.nio.file.{Files, Paths}
import java.util.concurrent.ThreadLocalRandom

import com.jeff.megaupload.constant.DataCollectState

import scala.io.StdIn._

object Main {

  private val writeChunk = 10000

  def main(args: Array[String]) {

    readLine("File Transfer?(true) or Create File(false)???:").toBoolean match {
      case true =>
        println("Four test suites will be run.")
        println("The order is as Follows:\n" +
          "[0]:Sliding Window - No Drops\n" +
          "[1]:Sliding Window - Drops\n" +
          "[2]:Stop And Wait  - No Drops\n" +
          "[3]:Stop And Wait  - Drops")
        println("Below, Enter the amount of times each test suite will be ran.")

        val testCount = readLine("Test Count:").toInt
        val serverAdd = readLine("Server Address:")
        val serverPort = readLine("Server Port:").toInt
        val fileName = readLine("File Name:")
        val filePath = readLine("File Path:")

        var current = DataCollectState.SLIDING_NO_DROPS

        var done = false
        while (!done) {
          val windowSize = current match {
            case DataCollectState.SLIDING_NO_DROPS | DataCollectState.SLIDING_WITH_DROPS => 500
            case _ => 1
          }

          for (i <- 0 until testCount) {
            new Client().uploadFile(fileName, getClass.getClassLoader.getResource("data.datafile").getPath,
              windowSize, InetAddress.getByName(serverAdd), serverPort)
            Thread.sleep(1500)
          }

          current = current match {
            case DataCollectState.SLIDING_NO_DROPS => DataCollectState.SLIDING_WITH_DROPS
            case DataCollectState.SLIDING_WITH_DROPS => DataCollectState.STOP_START_NO_DROPS
            case DataCollectState.STOP_START_NO_DROPS => DataCollectState.STOP_START_WITH_DROPS
            case _ =>
              done = true
              DataCollectState.STOP_START_WITH_DROPS
          }
        }

      case _ =>
        val fileName = readLine("Enter File Name:")
        val size = readLine("Enter File Size In Mega Bytes:").toInt * 1000000
        createFile(size, fileName)
    }


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
