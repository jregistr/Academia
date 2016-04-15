package com.jeff.megaupload.client

import java.net.DatagramPacket
import java.nio.ByteBuffer

import com.jeff.megaupload.constant.Constants

import scala.collection.mutable.ArrayBuffer


object Main {

  def main(args: Array[String]) {

    val byteBuffer = ByteBuffer.allocate(1000)
    byteBuffer.put(Constants.intToByteArray(10))
    byteBuffer.put(Constants.intToByteArray(2))

    val byteArray = byteBuffer.array()

  /*  val processed = seqAndPayload(byteArray)
//    println(processed)*/

  }




  /*
  *  val stream = new FileInputStream(getClass.getClassLoader.getResource("TCPThroughClient.scala").getPath)
    var done = false
    val buffer = new Array[Byte](1000)
    val outStream = new FileOutputStream("out.txt")
    while (!done) {
      (for (i <- 0 until 500 if !done) yield {
        val readCount = stream.read(buffer)
        if (readCount != -1) {
          // Some((readCount, buffer.slice(0, readCount)))
          Some(buffer.slice(0, readCount))
        } else {
          done = true
          None
        }
      }).filter(_.isDefined).map(_.get).toList.foreach(bytes => {
        outStream.write(bytes)
      })
    }

    outStream.flush()
    outStream.close()
  *
  * */

}
