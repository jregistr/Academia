package com.jeff.chaser.models.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.jeff.chaser.models.components.{AccelerationComponent, VelocityComponent}


class AccelerationSystem extends IteratingSystem(
  Family.all(classOf[VelocityComponent], classOf[AccelerationComponent]).get()) {

  private val v = ComponentMapper.getFor(classOf[VelocityComponent])
  private val a = ComponentMapper.getFor(classOf[AccelerationComponent])

  override def processEntity(entity: Entity, deltaTime: Float): Unit =
    v.get(entity).velocity.add(a.get(entity).acceleration)
}
