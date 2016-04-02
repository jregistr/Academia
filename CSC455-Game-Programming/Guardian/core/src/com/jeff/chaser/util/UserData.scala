package com.jeff.chaser.util


sealed trait UserData {

  val name: String

}

case class BuildingIdentifier(name: String) extends UserData

case class PlayerIdentifier(name: String) extends UserData

case class GuardIdentifier(name: String) extends UserData
