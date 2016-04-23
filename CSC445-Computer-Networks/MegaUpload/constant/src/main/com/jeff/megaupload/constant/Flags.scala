package com.jeff.megaupload.constant


object Flags {

  sealed abstract class Flag(val id: Int)

  case object INIT extends Flag(-1)

  case object END extends Flag(-2)

}
