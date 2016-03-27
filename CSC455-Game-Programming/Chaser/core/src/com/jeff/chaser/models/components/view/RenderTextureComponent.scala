package com.jeff.chaser.models.components.view

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion


class RenderTextureComponent(var tex: TextureRegion, val initWidth: Float, val initHeight: Float)
  extends Component {
  var sX = 1f
  var sY = 1f
  var color = Color.WHITE
  val oX = initWidth / 2.0f
  val oY = initHeight / 2.0f

  def this(tex: TextureRegion, initWidth: Float, initHeight: Float, sX: Float, sY: Float, color: Color) {
    this(tex, initWidth, initHeight)
    this.sX = sX
    this.sY = sY
    this.color = color
  }

}
