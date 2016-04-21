package com.jeff.megaupload.server.util.scribe

import java.io.FileOutputStream
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.locks.LockSupport.{park, unpark}

/**
  * Class to make a writer utility class responsible for allowing concurrently writing and continuing data transfer.
  *
  * @param fileName The name of the file this scribe will write.
  */
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

  /**
    * Method to queue up bytes to be written into file.
    *
    * @param bytes The bytes to add.
    */
  def write(bytes: Array[Byte]): Unit = {
    work.add(bytes)
    unpark(this)
  }

  /**
    * Method to finish off writing.
    */
  def finish(): Unit = {
    finished = true
    doWork()
    stream.flush()
    stream.close()
    unpark(this)
  }

  /**
    * Does the actual data writing.
    */
  private def doWork(): Unit = {
    while (work.peek() != null) {
      stream.write(work.poll())
    }
    stream.flush()
  }

}
