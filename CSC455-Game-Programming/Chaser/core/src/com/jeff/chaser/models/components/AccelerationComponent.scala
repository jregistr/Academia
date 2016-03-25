package com.jeff.chaser.models.components

import com.badlogic.ashley.core.Component
import com.jeff.chaser.models.util.AccelerationState

class AccelerationComponent(val x: Float, val y: Float, var state: AccelerationState.Value) extends Component
