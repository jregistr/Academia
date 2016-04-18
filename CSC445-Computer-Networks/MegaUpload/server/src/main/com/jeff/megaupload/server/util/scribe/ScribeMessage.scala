package com.jeff.megaupload.server.util.scribe

sealed trait ScribeMessage

case class Write(val data: List[Array[Byte]]) extends ScribeMessage

case class Finish() extends ScribeMessage