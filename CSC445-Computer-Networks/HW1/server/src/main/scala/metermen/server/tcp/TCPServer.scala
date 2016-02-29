package metermen.server.tcp

import java.io.{DataOutputStream, DataInputStream}
import java.net.{Socket, ServerSocket}

/**
  * TCP server.
  */
abstract class TCPServer(private val server:ServerSocket) {

  protected var connection:Socket = _
  protected var input:DataInputStream = _
  protected var output:DataOutputStream = _

  final def connectionWait():Unit = {
    while (connection == null){
      connection = server.accept()
      println(s"Made Connection:${connection.getInetAddress}")
      input = new DataInputStream(connection getInputStream())
      output = new DataOutputStream(connection getOutputStream())
      output.flush()
    }
    process()
  }

  protected def process():Unit

}
