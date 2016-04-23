package com.jeff.megaupload.server

import java.io.FileOutputStream

import com.jeff.megaupload.constant.DataCollectState
import DataCollectState.DataCollectState

import scala.collection.mutable.{Map => MutMap}
import scala.io.StdIn._

object Main {
  def main(args: Array[String]) {

    val collectStateNames = Map(
      DataCollectState.SLIDING_NO_DROPS -> "Sliding Window No Drops",
      DataCollectState.SLIDING_WITH_DROPS -> "Sliding Window Drops",
      DataCollectState.STOP_START_NO_DROPS -> "Stop and Wait No Drops",
      DataCollectState.STOP_START_WITH_DROPS -> "Stop And Wait Drops"
    )

    println("Four test suites will be run.")
    println("The order is as Follows:\n" +
      "[0]:Sliding Window - No Drops\n" +
      "[1]:Sliding Window - Drops\n" +
      "[2]:Stop And Wait  - No Drops\n" +
      "[3]:Stop And Wait  - Drops")
    println("Below, Enter the amount of times each test suite will be ran.")

    val testCounts = readLine("Enter amount of tests to run:").toInt
    val port = readLine("Enter Port:").toInt

    val dataMap = MutMap[DataCollectState, List[Double]]()
    var currentState = DataCollectState.SLIDING_NO_DROPS


    var done = false

    while (!done) {

      val drops = currentState match {
        case DataCollectState.SLIDING_WITH_DROPS | DataCollectState.STOP_START_WITH_DROPS => true
        case _ => false
      }

      dataMap += currentState -> new Server(port, drops, testCounts).listen()

      currentState = currentState match {
        case DataCollectState.SLIDING_NO_DROPS => DataCollectState.SLIDING_WITH_DROPS
        case DataCollectState.SLIDING_WITH_DROPS => DataCollectState.STOP_START_NO_DROPS
        case DataCollectState.STOP_START_NO_DROPS => DataCollectState.STOP_START_WITH_DROPS
        case _ =>
          done = true
          DataCollectState.STOP_START_WITH_DROPS
      }
    }

    val slideNo = dataMap.get(DataCollectState.SLIDING_NO_DROPS).get.iterator
    val slideDrop = dataMap.get(DataCollectState.SLIDING_WITH_DROPS).get.iterator
    val stopNo = dataMap.get(DataCollectState.STOP_START_NO_DROPS).get.iterator
    val stopDrop = dataMap.get(DataCollectState.STOP_START_WITH_DROPS).get.iterator

    val stream = new FileOutputStream("Results.csv")

    implicit def nameToBytes(value: String): Array[Byte] = {
      value.getBytes("UTF-8")
    }

    implicit def doubleToBytes(value: Double): Array[Byte] = {
      nameToBytes(String.valueOf(value))
    }

    stream.write(collectStateNames.get(DataCollectState.SLIDING_NO_DROPS).get.concat(","))
    stream.write(collectStateNames.get(DataCollectState.SLIDING_WITH_DROPS).get.concat(","))
    stream.write(collectStateNames.get(DataCollectState.STOP_START_NO_DROPS).get.concat(","))
    stream.write(collectStateNames.get(DataCollectState.STOP_START_WITH_DROPS).get.concat("\n"))

    while (slideNo.hasNext) {
      stream.write(slideNo.next())
      stream.write(",")

      stream.write(slideDrop.next())
      stream.write(",")

      stream.write(stopNo.next())
      stream.write(",")

      stream.write(stopDrop.next())
      stream.write("\n")

    }

  }
}
