package com.jeff.megaupload.constant


object Flags {

  sealed abstract class Flag(val identifier: Int)

  case object RESEND_HIGHEST extends Flag(-1)

  case object RESEND_NAME extends Flag(-2)

  case object INFO_NAME extends Flag(-3)

}
