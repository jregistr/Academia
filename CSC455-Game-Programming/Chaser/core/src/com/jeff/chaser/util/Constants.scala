package com.jeff.chaser.util

import com.badlogic.gdx.graphics.Texture


object Constants {
  val TITLE = "Run Awaaay!!"
  val WIDTH = 1500
  val HEIGHT = 700

  object TexConstants {

    private val TAIL = ".png"

    val GROUND = s"ground$TAIL"
    val HOUSE = s"house$TAIL"
    val TANKS = s"tanks$TAIL"
    val TANKS_PER_LINE = 8
    val TANKS_NUM_LINES = 8

    def grab(key:String, map:Map[String, Texture]):Texture = map.get(key) match {
      case Some(tex) => tex
      case _ => throw new NullPointerException ("Missing required tex")
    }

  }

}
