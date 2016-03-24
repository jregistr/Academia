package com.jeff.chaser.models.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class VelocityComponent(val velocity: Vector2, val maxX:Float, val maxY:Float) extends Component
