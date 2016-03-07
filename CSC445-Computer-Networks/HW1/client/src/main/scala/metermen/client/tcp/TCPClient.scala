package metermen.client.tcp

import java.io.{DataOutputStream, DataInputStream}
import java.net.{InetAddress, Socket}
import java.text.SimpleDateFormat
import java.util.Date

import scala.collection.mutable.ListBuffer

/**
  * Class to describe abstract tcp client.
  */
abstract class TCPClient(address: String, port: Int, val name: String) {

  protected final val TEST_COUNT = 1000

  protected val socket = new Socket(InetAddress.getByName(address), port)
  socket.setTcpNoDelay(true)
  protected val input = new DataInputStream(socket getInputStream())
  protected val output = new DataOutputStream(socket getOutputStream())

  final def fileName(): String = {
    s"./$name.csv"
  }

  def process(): List[(String, List[Double])]

}
