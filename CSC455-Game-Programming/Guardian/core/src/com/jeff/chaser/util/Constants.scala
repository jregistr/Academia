package com.jeff.chaser.util

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils


object Constants {
  /*val TITLE = "Run Awaaay!!"
  val WIDTH = 1500
  val HEIGHT = 700*/

  val TITLE = "Guardian"
  val PIX_TO_M = 0.05f
  val M_TO_PIX = 20f

  val WIDTH = MathUtils.round(toPix(75.0f))
  val HEIGHT = MathUtils.round(toPix(35.0f))

  def toMeters(pix: Float): Float = {
    pix * PIX_TO_M
  }

  def toPix(m: Float): Float = {
    m * M_TO_PIX
  }

  object TexConstants {

    private val TAIL = ".png"

    val GROUND = s"ground$TAIL"
    val HOUSE = s"house$TAIL"
    val TANKS = s"tanks$TAIL"
    val TANKS_PER_LINE = 8
    val TANKS_NUM_LINES = 8

    def grab(key: String, map: Map[String, Texture]): Texture = map.get(key) match {
      case Some(tex) => tex
      case _ => throw new NullPointerException("Missing required tex")
    }

  }

}
