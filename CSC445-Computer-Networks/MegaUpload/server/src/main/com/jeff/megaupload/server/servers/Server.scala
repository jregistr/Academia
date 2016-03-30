package com.jeff.megaupload.server.servers

import java.net.{InetAddress, DatagramSocket}

/**
  * Class to represent an abstract server.
  */
abstract class Server(port:Int, localAddress:String) {

  protected val socket = new DatagramSocket(port, InetAddress.getLocalHost)

}
