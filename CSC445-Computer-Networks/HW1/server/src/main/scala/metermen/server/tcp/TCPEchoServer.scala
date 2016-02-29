package metermen.server.tcp

import java.io.{DataOutputStream, DataInputStream}
import java.net.{Socket, ServerSocket}


class TCPEchoServer(server:ServerSocket) extends TCPServer(server){

  override def process(): Unit = {
    while (true){
      val nextType = input.readInt()
      val bytes = new Array[Byte](nextType)
      input.read(bytes)
      output.write(bytes)
      output.flush()
    }
  }

}

object TCPEchoServer{
  def apply(port:Int):TCPEchoServer={
    new TCPEchoServer(new ServerSocket(port, 1))
  }
}
