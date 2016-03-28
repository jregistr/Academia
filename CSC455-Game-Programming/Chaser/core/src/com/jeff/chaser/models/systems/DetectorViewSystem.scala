package com.jeff.chaser.models.systems

import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import com.badlogic.gdx.math.MathUtils
import com.jeff.chaser.models.components.ai.DetectorComponent


object DetectorViewSystem {

  private val res = 100

  def makeDetectorCone(dc: DetectorComponent): TextureRegion = {
    val radius = dc.distance
    val fov = dc.fovAngle
    val pix = new Pixmap(MathUtils.round(radius + MathUtils.round(radius * 0.01f)), MathUtils.round(radius * 2), Format.RGBA8888)
    pix.setColor(Color.RED)

    val stepCount = MathUtils.round(fov * res)
    val stepAngleSize = fov / stepCount
    var curAngle = -(fov / 2.0f)
    val roundRad = MathUtils.round(radius)
    val origin = (0, roundRad)

    var x = radius * MathUtils.cosDeg(curAngle) + (origin._1 * 2)
    var y = radius * MathUtils.sinDeg(curAngle) + origin._2

    for (_ <- 0 to stepCount) {
      curAngle += stepAngleSize
      val x2 = radius * MathUtils.cosDeg(curAngle) + (origin._1 * 2)
      val y2 = radius * MathUtils.sinDeg(curAngle) + origin._2
      pix.fillTriangle(origin._1, origin._2, MathUtils.round(x), MathUtils.round(y), MathUtils.round(x2), MathUtils.round(y2))
      x = x2
      y = y2
    }
    val texture = new Texture(pix, false)
    pix.dispose()
    new TextureRegion(texture)
  }

}
