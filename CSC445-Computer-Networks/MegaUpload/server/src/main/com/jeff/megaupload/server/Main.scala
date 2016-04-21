package com.jeff.megaupload.server

import com.jeff.megaupload.server.servers.{SlidingWindow, StopAndWait}

object Main {
  def main(args: Array[String]) {
    //new SlidingWindow(7000, "localhost", true)
    new StopAndWait(7000, "localhost", true)
  }
}
