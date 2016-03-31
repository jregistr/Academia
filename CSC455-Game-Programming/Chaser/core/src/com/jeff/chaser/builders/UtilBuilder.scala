package com.jeff.chaser.builders

import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import com.badlogic.gdx.math.MathUtils


object UtilBuilder {

  private val thickness = 10

  def makeDetectorCone(radius: Float, fov: Float): TextureRegion = {
    val pix = new Pixmap(MathUtils.round(radius + MathUtils.round(radius * 0.01f)), MathUtils.round(radius * 2), Format.RGBA8888)
    pix.setColor(new Color(1, 0, 0, 0.4f))

    val stepCount = 1000
    val stepAngleSize = fov / stepCount
    var curAngle = -(fov / 2.0f)
    val roundRad = MathUtils.round(radius)
    val origin = (0, roundRad)

    var x = radius * MathUtils.cosDeg(curAngle) + (origin._1 * 2)
    var y = radius * MathUtils.sinDeg(curAngle) + origin._2

    /*for (_ <- 0 to stepCount) {
      curAngle += stepAngleSize
      val x2 = radius * MathUtils.cosDeg(curAngle) + (origin._1 * 2)
      val y2 = radius * MathUtils.sinDeg(curAngle) + origin._2
      pix.fillTriangle(origin._1, origin._2, MathUtils.round(x), MathUtils.round(y), MathUtils.round(x2), MathUtils.round(y2))
      x = x2
      y = y2
    }*/

    pix.drawLine(origin._1, origin._2, MathUtils.round(x), MathUtils.round(y))

    for (_ <- 0 to stepCount) {
      curAngle += stepAngleSize
      val x2 = radius * MathUtils.cosDeg(curAngle) + (origin._1 * 2)
      val y2 = radius * MathUtils.sinDeg(curAngle) + origin._2

      pix.drawLine(MathUtils.round(x), MathUtils.round(y), MathUtils.round(x2), MathUtils.round(y2))

      x = x2
      y = y2
    }
    pix.drawLine(origin._1, origin._2, MathUtils.round(x), MathUtils.round(y))

    val texture = new Texture(pix, false)
    pix.dispose()
    new TextureRegion(texture)
  }

  /*private def recBetween(x: Float, y: Float, x2: Float, y2: Float): ((Int, Int), (Int, Int), (Int, Int), (Int, Int)) = {
    val toPoint2 = new Vector2(x2, y2).nor()
    val t = thickness / 2.0f

    implicit def round(float: Float): Int = MathUtils.round(float)

    val n = new Vector2(toPoint2.y, -toPoint2.x).nor()
    (
      (x + (n.x * t), y + (n.y * t)),
      (x - (n.x * t), y - (n.y * t)),
      (x2 + (n.x * t), y2 + (n.y * t)),
      (x2 - (n.x * t), y2 - (n.y * t))
      )
  }*/

}
