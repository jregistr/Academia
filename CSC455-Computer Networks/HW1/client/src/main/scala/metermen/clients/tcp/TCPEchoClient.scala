package metermen.clients.tcp

import java.io.{DataOutputStream, DataInputStream}
import java.net.{InetAddress, Socket}

/**
  * tcp echo client
  */
class TCPEchoClient(private val socket: Socket) {

  private val input = new DataInputStream(socket getInputStream())
  private val output = new DataOutputStream(socket getOutputStream())

  def process():Unit= {

  }

}

object TCPEchoClient{
  def apply(address:String, port:Int): Unit ={
    new TCPEchoClient(new Socket(InetAddress.getByName(address), port))
  }
}
