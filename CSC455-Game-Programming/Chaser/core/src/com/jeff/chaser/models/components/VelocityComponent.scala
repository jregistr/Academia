package com.jeff.chaser.models.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class VelocityComponent(var x:Float, var y:Float, val maxX:Float, val maxY:Float) extends Component{
  var targetX:Float = 0
  var targetY:Float = 0
}
