package com.jeff.megaupload.server

import com.jeff.megaupload.server.servers.SlidingWindow
import com.jeff.megaupload.server.util.scribe.Scribe


object Main {
  def main(args: Array[String]) {
    new SlidingWindow(7000, "localhost", false)
  }
}
