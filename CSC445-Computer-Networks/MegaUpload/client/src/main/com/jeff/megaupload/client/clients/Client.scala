package com.jeff.megaupload.client.clients

import java.io.FileInputStream
import java.net.{DatagramPacket, DatagramSocket, InetAddress, SocketTimeoutException}

import com.jeff.megaupload.constant.Constants._
import com.jeff.megaupload.constant.{Constants, Flags}

/**
  * Class to represent an abstract transfer client.
  *
  * @param localAddress The local address for this client.
  * @param localPort    The local port for this client.
  */
abstract class Client(localAddress: String, localPort: Int) {

  protected val socket = new DatagramSocket(localPort, InetAddress.getByName(localAddress))
  protected val readPacket = new DatagramPacket(new Array[Byte](PACKET_SIZE), 0, PACKET_SIZE)
  protected val writePacket = new DatagramPacket(new Array[Byte](PACKET_SIZE), 0, PACKET_SIZE)

  protected val PACKETS_IN_CHUNKS = 100000
  protected val windowSize = 500

  protected val buffer = new Array[Byte](PAYLOAD_SIZE)

  socket.setSoTimeout(1000)

  /**
    * Method called to process the file transfer with the server.
    *
    * @param stream      The file stream to pull data from.
    * @param destAddress The destination address.
    * @param destPort    The destination port.
    */
  protected def processFileTransfer(stream: FileInputStream, destAddress: InetAddress, destPort: Int): Unit

  /**
    * Processes transferring a file to a destination.
    *
    * @param path        The path of the file.
    * @param destAddress The address of the destination.
    * @param destPort    The port of the destination.
    */
  def upload(path: String, destAddress: String, destPort: Int): Unit = {
    val inetAddress = InetAddress.getByName(destAddress)
    val last = path.lastIndexOf("/")
    val fn = last match {
      case -1 => path
      case _ => path.substring(last + 1, path.length)
    }

    beginTransfer(fn, inetAddress, destPort)

    processFileTransfer(new FileInputStream(path), inetAddress, destPort)

    endTransfer(inetAddress, destPort)
  }

  /**
    * Method to do the beginning procedures for transferring the file.
    *
    * @param fileName    The name of the file.
    * @param destAddress The destination address.
    * @param destPort    The destination port.
    */
  private def beginTransfer(fileName: String, destAddress: InetAddress, destPort: Int): Unit = {
    val startPacket = Constants.makePacket(-windowSize, fileName.getBytes("UTF-8"), destAddress, destPort)
    socket.send(startPacket)
    var acknowledged = false
    while (!acknowledged) {
      try {
        socket.receive(readPacket)
        val extracted = Constants.seqAndPayload(readPacket)
        extracted._1 match {
          case Flags.INFO_NAME.identifier => acknowledged = true
          case _ => acknowledged = true
        }
      } catch {
        case s: SocketTimeoutException =>
          socket.send(startPacket)
        case t: Throwable => throw t
      }
    }
  }

  /**
    * Method to do the ending procedures for file transfer.
    *
    * @param destAddress The destination address.
    * @param destPort    The destination port.
    */
  private def endTransfer(destAddress: InetAddress, destPort: Int): Unit = {
    val endOfTransfer = Constants.makePacket(Flags.END_OF_TRANSFER.identifier, "".getBytes, destAddress, destPort)
    socket.send(endOfTransfer)
    var acknowledged = false
    while (!acknowledged) {
      try {
        socket.receive(readPacket)
        val extracted = Constants.seqAndPayload(readPacket)
        val seq = extracted._1
        seq match {
          case Flags.END_OF_TRANSFER.identifier => acknowledged = true
          case _ => socket.send(endOfTransfer)
        }
      } catch {
        case s: SocketTimeoutException => socket.send(endOfTransfer)
        case t: Throwable => throw t
      }
    }
  }

}
