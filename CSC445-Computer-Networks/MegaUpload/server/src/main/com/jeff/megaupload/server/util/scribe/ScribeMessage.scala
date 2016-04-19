package com.jeff.megaupload.server.util.scribe

/**
  * Trait to describe base message for scribe.
  */
sealed trait ScribeMessage

/**
  * Write message.
  *
  * @param data The data to write.
  */
case class Write(data: Array[Byte]) extends ScribeMessage

/**
  * Finish message.
  */
case class Finish() extends ScribeMessage