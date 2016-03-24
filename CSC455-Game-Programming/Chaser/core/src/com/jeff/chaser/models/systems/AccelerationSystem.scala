package com.jeff.chaser.models.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.jeff.chaser.models.components.{AccelerationComponent, VelocityComponent}


class AccelerationSystem extends IteratingSystem(
  Family.all(classOf[VelocityComponent], classOf[AccelerationComponent]).get()) {

  private val vm = ComponentMapper.getFor(classOf[VelocityComponent])
  private val am = ComponentMapper.getFor(classOf[AccelerationComponent])

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val velocityComponent = vm.get(entity)
    val acc = am.get(entity).acceleration
    val vel = velocityComponent.velocity

    vel.set(clamp(vel.x + acc.x, velocityComponent.maxX), clamp(vel.y + acc.y, velocityComponent.maxY))
  }

  private def clamp(value:Float, max:Float):Float = {
    MathUtils.clamp(value, -max, max)
  }

}
