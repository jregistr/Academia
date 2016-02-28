package metermen.servers.tcp

import java.io.{DataOutputStream, DataInputStream}
import java.net.{Socket, ServerSocket}


class TCPEcho (private val server:ServerSocket) {

  private var connection:Socket = _
  private var input:DataInputStream = _
  private var output:DataOutputStream = _

  def start():Unit={
    while (connection == null){
      connection = server.accept()
      input = new DataInputStream(connection getInputStream())
      output = new DataOutputStream(connection getOutputStream())
      output.flush()
    }
    readData()
  }

  private def readData():Unit={
    while (true){
      val nextType = input.readInt()
      val bytes = new Array[Byte](nextType)
      input.read(bytes)
      output.write(bytes)
      output.flush()
    }
  }

}

object TCPEcho{
  def apply(port:Int):TCPEcho={
    new TCPEcho(new ServerSocket(port, 1))
  }
}
