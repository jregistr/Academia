package com.jeff.chaser.models.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.jeff.chaser.models.components.{TransformComponent, VelocityComponent}


class VelocitySystem extends IteratingSystem(Family.all(classOf[VelocityComponent], classOf[TransformComponent]).get()) {

  private val vm = ComponentMapper.getFor(classOf[VelocityComponent])
  private val tm = ComponentMapper.getFor(classOf[TransformComponent])

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val trans = tm.get(entity)
    val vel = vm.get(entity)
    trans.x += (clamp(vel.x, vel.maxX) * deltaTime)
    trans.y += (clamp(vel.y, vel.maxY) * deltaTime)
    vel.targetX = clamp(vel.targetX, vel.maxX)
    vel.targetY = clamp(vel.targetY, vel.maxY)
  }

  private def clamp(value: Float, max: Float): Float = {
    MathUtils.clamp(value, -max, max)
  }

}
