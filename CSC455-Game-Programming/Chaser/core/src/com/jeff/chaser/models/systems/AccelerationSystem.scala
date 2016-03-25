package com.jeff.chaser.models.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.jeff.chaser.models.components.{AccelerationComponent, VelocityComponent}


class AccelerationSystem extends IteratingSystem(
  Family.all(classOf[VelocityComponent], classOf[AccelerationComponent]).get()) {

  private val vm = ComponentMapper.getFor(classOf[VelocityComponent])
  private val am = ComponentMapper.getFor(classOf[AccelerationComponent])

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val vel = vm.get(entity)
    val acc = am.get(entity)

    def calculateForAxis(cur: Float, target: Float, accel: Float): Float = {
      var out = 0f
      if (cur != target) {
        val dir = if (cur < target) 1 else -1
        out = Math.min((accel * dir) * deltaTime, Math.abs(target - cur))
      }
      out
    }

    vel.x += calculateForAxis(vel.x, vel.targetX, acc.x)
    vel.y += calculateForAxis(vel.y, vel.targetY, acc.y)

  }

}
