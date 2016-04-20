package com.jeff.megaupload.client.util

import java.net.DatagramPacket

import org.apache.commons.pool2.impl.GenericObjectPool


object PacketPool {

  private val pool = new GenericObjectPool[DatagramPacket](new PacketFactory)

  def get(): DatagramPacket = {
    var packet: Option[DatagramPacket] = None
    do {
      try {
        val out = pool.borrowObject()
        if (out != null) {
          packet = Some(out)
        }
      } catch {
        case e: Exception =>
        case t: Throwable => throw t
      }
    }
    while (packet.isEmpty)
  }

  def takeBack(packet: DatagramPacket): Unit = {
    pool.returnObject(packet)
  }

}
