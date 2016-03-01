package metermen.server.tcp

import java.net.ServerSocket
import com.jeff.dsl.util.Util._

/**
  * TCP server for measuring throughput
  */
class TCPThroughServer(server: ServerSocket) extends TCPServer(server) {

  override protected def process(): Unit = {
    {
      val spTest = new Array[Byte](1)
      val loopCount = input.readInt()
      loop(loopCount, () => {
        input.read(spTest)
        output.write(spTest)
        output.flush()
      })
    }

    {
      val oneByte = new Array[Byte](1)
      loop(input.readInt(), () => {
        val size = input.readInt()
        println("Size:" + size)
        input.read(new Array[Byte](size))
        output.write(oneByte)
        output.flush()
      })
    }
  }

}

object TCPThroughServer {
  def apply(port: Int): TCPThroughServer = {
    new TCPThroughServer(new ServerSocket(port, 1))
  }
}
