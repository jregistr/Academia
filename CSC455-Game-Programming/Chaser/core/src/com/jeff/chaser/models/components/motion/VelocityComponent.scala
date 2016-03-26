package com.jeff.chaser.models.components.motion

import com.badlogic.ashley.core.Component

class VelocityComponent(var x:Float, var y:Float, val maxX:Float, val maxY:Float) extends Component{
  var targetX:Float = 0
  var targetY:Float = 0
}
