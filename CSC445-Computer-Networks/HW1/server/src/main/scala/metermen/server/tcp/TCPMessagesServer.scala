package metermen.server.tcp

import java.net.ServerSocket
import com.jeff.dsl.util.Util._

class TCPMessagesServer(server: ServerSocket) extends TCPServer(server) {

  override protected def process(): Unit = {
    val oneByte = new Array[Byte](1)

    val testCount = input.readInt()

    loop(testCount, () => {
      val tests = input.readInt()
      val size = input.readInt()

      val readBuffer = new Array[Byte](size)
      loop(tests, () => {
        input.readFully(readBuffer)
      })
      output.write(oneByte)
      output.flush()

    })

  }

}

object TCPMessagesServer{
  def apply(port:Int): TCPMessagesServer ={
    new TCPMessagesServer(new ServerSocket(port, 1))
  }
}
