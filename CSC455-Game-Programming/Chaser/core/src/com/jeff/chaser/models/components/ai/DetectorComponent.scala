package com.jeff.chaser.models.components.ai

import com.badlogic.ashley.core.{Entity, Component}
import com.badlogic.ashley.utils.ImmutableArray

class DetectorComponent(val fovAngle: Float, val distance: Float, val oX: Float, val oY: Float) extends Component {
  var detectedLastCheck: Option[ImmutableArray[Entity]] = None
}