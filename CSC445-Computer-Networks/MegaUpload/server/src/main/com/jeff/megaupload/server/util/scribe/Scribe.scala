package com.jeff.megaupload.server.util.scribe

import java.io.FileOutputStream
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.locks.LockSupport.{park, unpark}


class Scribe(fileName: String) extends Thread {

  private val stream = new FileOutputStream(fileName)
  private val work = new ConcurrentLinkedQueue[Array[Byte]]()
  private var finished = false

  override def run(): Unit = {

    while (!finished) {
      doWork()
      park()
    }

  }

  def write(bytes: Array[Byte]): Unit = {
    work.add(bytes)
    unpark(this)
  }

  def finish(): Unit = {
    finished = true
    doWork()
    stream.flush()
    stream.close()
    unpark(this)
  }

  private def doWork(): Unit = {
    while (work.peek() != null) {
      stream.write(work.poll())
    }
    stream.flush()
  }

}
