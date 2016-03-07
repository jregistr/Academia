package metermen.server.udp


class UDPEchoServer(size: Int, localPort: Int) extends UDPServer(size, localPort) {

  override def process(): Unit = {
    println(s"STARTED on port $localPort")
    while (true){
      socket.receive(readPacket)
     // println("Received Packet")
      writePacket.setAddress(readPacket.getAddress)
      writePacket.setPort(readPacket.getPort)
      socket.send(writePacket)
    }
  }

}
