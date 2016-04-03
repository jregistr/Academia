package com.jeff.chaser.util

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.utils.{Array => LibArray}
import com.jeff.chaser.models.util.AnimInfo


object Constants {
  val TITLE = "Run Awaaay!!"
  val WIDTH = 1500
  val HEIGHT = 700

  val PATROL_X = (WIDTH * 0.7f) - 128
  val PATROL_Y = (HEIGHT * 0.75f) - 128

  def splitTankSheet(sheet: Texture): Array[Array[TextureRegion]] = {
    TextureRegion.split(sheet,
      sheet.getWidth / TexConstants.TANKS_PER_LINE,
      sheet.getHeight / TexConstants.TANKS_NUM_LINES)
  }

  def getLineFromSheet(array: Array[Array[TextureRegion]], index: Int): LibArray[TextureRegion] = {
    val line = array(index)
    val out = new LibArray[TextureRegion]()
    line.foreach(out.add)
    out
  }

  def makeAnim(playMode: PlayMode, frameRate: Float, frames: LibArray[TextureRegion]): AnimInfo = {
    val anim = new Animation(frameRate, frames, playMode)
    new AnimInfo(anim)
  }

  def centerTex(raw:Float, sideLength:Float):Float = raw - sideLength / 2.0f

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
