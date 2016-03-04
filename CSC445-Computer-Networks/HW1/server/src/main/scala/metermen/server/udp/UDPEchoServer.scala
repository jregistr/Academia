package metermen.server.udp


class UDPEchoServer(size: Int, localPort: Int, destUri: String, destPort: Int)
  extends UDPServer(size, localPort, destUri, destPort) {

  println("WritePacket:" + writePacket.getLength)

  override def process(): Unit = {
    while (true){
      socket.receive(readPacket)
      socket.send(writePacket)
    }
  }

}
