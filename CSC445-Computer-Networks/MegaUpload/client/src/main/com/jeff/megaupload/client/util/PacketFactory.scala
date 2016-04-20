package com.jeff.megaupload.client.util

import java.net.DatagramPacket

import com.jeff.megaupload.constant.Constants
import org.apache.commons.pool2.impl.DefaultPooledObject
import org.apache.commons.pool2.{BasePooledObjectFactory, PooledObject}

/**
  * Factory to handle creating packets.
  */
class PacketFactory extends BasePooledObjectFactory[DatagramPacket] {

  override def wrap(obj: DatagramPacket): PooledObject[DatagramPacket] = new DefaultPooledObject[DatagramPacket](obj)

  override def create(): DatagramPacket = new DatagramPacket(new Array[Byte](Constants.PACKET_SIZE), 0, Constants.PACKET_SIZE)

}
