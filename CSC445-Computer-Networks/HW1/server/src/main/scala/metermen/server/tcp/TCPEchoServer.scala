package metermen.server.tcp

import java.net.ServerSocket

import com.jeff.dsl.util.Util._


class TCPEchoServer(server: ServerSocket) extends TCPServer(server) {

  override def process(): Unit = {
    val loopCount = input.readInt()
    loop(loopCount, () => {
      val nextType = input.readInt()
      val bytes = new Array[Byte](nextType)
      input.read(bytes)
      output.write(bytes)
      output.flush()
    })
  }

}

object TCPEchoServer {
  def apply(port: Int): TCPEchoServer = {
    new TCPEchoServer(new ServerSocket(port, 1))
  }
}
