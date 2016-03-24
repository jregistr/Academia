package com.jeff.chaser.models.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion


class RenderComponent(val tex: TextureRegion, val initWidth: Float, val initHeight: Float,
                      var sX: Float, var sY: Float, var color: Color) extends Component {
  val oX = initWidth / 2.0f
  val oY = initHeight / 2.0f
}
