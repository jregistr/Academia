package metermen.client.tcp

import java.io.{DataOutputStream, DataInputStream}
import java.net.{InetAddress, Socket}
import java.text.SimpleDateFormat
import java.util.Date

import scala.collection.mutable.ListBuffer

/**
  * Class to describe abstract tcp client.
  */
abstract class TCPClient(address: String, port: Int, val name:String) {

  protected val socket = new Socket(InetAddress.getByName(address), port)
  socket.setTcpNoDelay(true)
  protected val input = new DataInputStream(socket getInputStream())
  protected val output = new DataOutputStream(socket getOutputStream())
  protected val format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")

  final def fileName():String = {
    s"./$name-${format.format(new Date())}.csv"
  }

  def process():ListBuffer[(String, List[Double])]

}
