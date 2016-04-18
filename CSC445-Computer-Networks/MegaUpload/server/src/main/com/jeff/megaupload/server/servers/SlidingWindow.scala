package com.jeff.megaupload.server.servers

import java.net.{DatagramPacket, InetAddress, SocketTimeoutException}

import akka.actor.ActorRef
import com.jeff.megaupload.constant.Flags
import com.jeff.megaupload.server.util.scribe.Scribe

import scala.collection.mutable.ListBuffer

class SlidingWindow(port: Int, localAddress: String, simDrops: Boolean) extends Server(port, localAddress, simDrops) {

  /**
    * Helper class represent a window item.
    *
    * @param ack  The ack number.
    * @param data The payload data.
    */
  class WindowItem(var ack: Int, var data: Array[Byte])


  /*  protected override def processFileTransfer(lastAck: Int, destAdd: InetAddress, destPort: Int): List[Array[Byte]] = {
      var done = false
      val fileData = new ListBuffer[Array[Byte]]()
      val window = createWindow(500)
      var position = 0
      var highestReceived = 0
      while (!done) {
        try {
          socket.receive(readPacket)
          val extracted = fromPacket(readPacket)
          val curAck = extracted._1

          curAck match {
            case Flags.RESEND_HIGHEST.identifier => sendAck(curAck, destAdd, destPort)
            case _ =>
              val piece = window(position)
              piece.ack = curAck
              piece.data = extracted._2
              position += 1
              highestReceived = curAck
              done = curAck == lastAck
          }
        } catch {
          case s: SocketTimeoutException =>
            slideWindow(window, fileData, position)
            position = 0
          case t: Throwable => throw t
        }
      }
      fileData.toList
    }*/


  /**
    * Method called to process a file transfer.
    *
    * @param lastAck  The last expected ack number.
    * @param scribe   The scribe actor to write the data to file.
    * @param destAdd  The address origin of the file.
    * @param destPort The port origin of the file.
    */
  override protected def processFileTransfer(lastAck: Int, scribe: ActorRef, destAdd: InetAddress, destPort: Int): Unit = {

  }

  /**
    * Method to entries to the start of the window from a point p and push all data from start to p into the output.
    *
    * @param window   The window to operate on.
    * @param out      The output.
    * @param position The position to work from.
    */
  private def slideWindow(window: Array[WindowItem], out: ListBuffer[Array[Byte]], position: Int): Unit = {
    for (i <- 0 to position) {
      out += window(i).data
      val op = i + position
      if (op < window.length) {
        window.update(i, window(op))
      }
    }
  }

  /**
    * Method to create a window.
    *
    * @param size The size of the window.
    * @return A new window.
    */
  private def createWindow(size: Int): Array[WindowItem] = {
    val window = new Array[WindowItem](size)
    for (i <- 0 until size) {
      window.update(i, new WindowItem(0, null))
    }
    window
  }

  override protected def fromPacket(packet: DatagramPacket): (Int, Array[Byte]) = seqAndPayload(packet)

}
