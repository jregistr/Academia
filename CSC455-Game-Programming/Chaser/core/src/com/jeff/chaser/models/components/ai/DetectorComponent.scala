package com.jeff.chaser.models.components.ai

import com.badlogic.ashley.core.{Component, Entity}
import com.badlogic.gdx.math.Polygon

class DetectorComponent(val fovAngle: Float, val distance: Float, val oX: Float, val oY: Float) extends Component {
  var detectedLastCheck: Entity = null
}