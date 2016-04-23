package com.jeff.megaupload.server

import scala.io.StdIn._

object Main {
  def main(args: Array[String]) {

    println("Four test suites will be run.")
    println("The order is as Follows:\n" +
      "[0]:Sliding Window - No Drops\n" +
      "[1]:Sliding Window - Drops\n" +
      "[2]:Stop And Wait  - No Drops\n" +
      "[3]:Stop And Wait  - Drops")
    println("Below, Enter the amount of times each test suite will be ran.")

    val testCounts = readLine("Enter amount of tests to run:").toInt

    val port = readLine("Enter Port:").toInt

    val sim = readLine("Simulate Drops?:").toBoolean

    new Server(port, sim, testCounts)
  }
}
