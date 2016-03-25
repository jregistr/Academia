package com.jeff.chaser.models.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.jeff.chaser.models.components.{AccelerationComponent, VelocityComponent}
import com.jeff.chaser.models.util.AccelerationState


class AccelerationSystem extends IteratingSystem(
  Family.all(classOf[VelocityComponent], classOf[AccelerationComponent]).get()) {

  private val vm = ComponentMapper.getFor(classOf[VelocityComponent])
  private val am = ComponentMapper.getFor(classOf[AccelerationComponent])

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val vel = vm.get(entity)
    val acc = am.get(entity)
    val state = acc.state
    if(state != AccelerationState.ZERO){
      val mult = if (state == AccelerationState.POSITIVE) 1 else -1
      vel.x += ((acc.x * mult) * deltaTime)
      vel.y += ((acc.y * mult) * deltaTime)
    }
  }

}
