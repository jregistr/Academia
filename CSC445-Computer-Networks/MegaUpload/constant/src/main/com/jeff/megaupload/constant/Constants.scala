package com.jeff.megaupload.constant


object Constants {

  val INT_BYTES = 4

  val PACKET_SIZE = 64000
  val PAYLOAD_SIZE = PACKET_SIZE - (INT_BYTES * 2)

  val TIME_OUT = 1

  val NANO_TO_SECONDS = 0.0000000001f

}
