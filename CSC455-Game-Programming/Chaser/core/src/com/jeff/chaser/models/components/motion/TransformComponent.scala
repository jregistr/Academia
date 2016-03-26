package com.jeff.chaser.models.components.motion

import com.badlogic.ashley.core.Component


class TransformComponent(var x:Float, var y:Float) extends Component {
  var rotation = 0f

  def this(x:Float, y:Float, rotation: Float) {
    this(x, y)
    this.rotation = rotation
  }
}
