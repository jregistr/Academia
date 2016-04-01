package com.jeff.chaser.models.components.ai.detect

import com.badlogic.ashley.core.{Component, Entity}

class DetectorComponent(val fovAngle: Float, val distance: Float, val oX: Float, val oY: Float) extends Component {
  var detectedLastCheck: Entity = null
}