package com.jeff.chaser.models.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.{Vector2, MathUtils}
import com.jeff.chaser.models.components.motion.{TransformComponent, VelocityComponent}
import com.jeff.chaser.models.components.util.ControlledComponent


class ControlSystem extends IteratingSystem(Family.all(classOf[ControlledComponent]).get()) {

  private val tm = ComponentMapper.getFor(classOf[TransformComponent])
  private val vm = ComponentMapper.getFor(classOf[VelocityComponent])

  private var turnDir = 0
  private var throttle = 0

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val t = tm.get(entity)
    val v = vm.get(entity)
    var end = t.rotation
    end += (turnDir match {
      case 0 => 0
      case 1 => -15.0f * deltaTime
      case _ => 15.0f * deltaTime
    })

    if (end < 0) {
      end = 360 - (-end)
    }

    t.rotation = end

    val x = MathUtils.cosDeg(end)
    val y = MathUtils.sinDeg(end)
    val vec = new Vector2(x, y)
    vec.nor()

    vec.scl(v.maxX)
    v.targetX = vec.x * throttle
    v.targetY = vec.y * throttle
  }

  def updateKeyStates(a: Boolean, s: Boolean, d: Boolean, w: Boolean): Unit = {
    if (w) {
      throttle = 1
    } else if (s) {
      throttle = -1
    } else {
      throttle = 0
    }
    if (a) {
      turnDir = -1
    } else if (d) {
      turnDir = 1
    } else {
      turnDir = 0
    }

  }

}
