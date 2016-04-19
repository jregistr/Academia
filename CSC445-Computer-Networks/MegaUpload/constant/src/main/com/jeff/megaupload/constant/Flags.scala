package com.jeff.megaupload.constant


object Flags {

  sealed abstract class Flag(val identifier: Int)

  case object NO_PACKET_RECEIVED extends Flag(0)

  case object RESEND_HIGHEST extends Flag(-1)

  case object RESEND_NAME extends Flag(-2)

  case object INFO_NAME extends Flag(-3)

  case object END_OF_TRANSFER extends Flag(-4)

}
