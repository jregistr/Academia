package com.jeff.megaupload.server

import java.io.FileOutputStream

import com.jeff.megaupload.constant.DataCollectState

import scala.io.StdIn._

object Main {
  def main(args: Array[String]) {

    val collectStateNames = Map(
      DataCollectState.SLIDING_NO_DROPS -> "Sliding Window No Drops",
      DataCollectState.SLIDING_WITH_DROPS -> "Sliding Window Drops",
      DataCollectState.STOP_START_NO_DROPS -> "Stop and Wait No Drops",
      DataCollectState.STOP_START_WITH_DROPS -> "Stop And Wait Drops"
    )

    val port = readLine("Enter Port:").toInt
    val testCounts = readLine("Enter amount of tests to run:").toInt

    val testInfo = readLine("Choose one of these four test suites.\n" +
      "[0]Sliding window no drops\n" +
      "[1]Sliding window with drops\n" +
      "[2]Stop and wait no drops\n" +
      "[3]Stop and wait with drops\n" +
      "What is your choice?:").toInt match {
      case 0 => (DataCollectState.SLIDING_NO_DROPS, false)
      case 1 => (DataCollectState.SLIDING_WITH_DROPS, true)
      case 2 => (DataCollectState.STOP_START_NO_DROPS, false)
      case 3 => (DataCollectState.STOP_START_WITH_DROPS, true)
      case _ => throw new IllegalArgumentException("Not a correct choice!")
    }

    val output = new Server(port, testInfo._2, testCounts).listen()

    implicit def nameToBytes(value: String): Array[Byte] = {
      value.getBytes("UTF-8")
    }

    val name = collectStateNames.get(testInfo._1).get
    val stream = new FileOutputStream(s"${name.replace(" ", "")}.csv")

    stream.write(s"$name\n")
    output.foreach(data => stream.write(s"$data\n"))
    stream.flush()
    stream.close()
  }
}
