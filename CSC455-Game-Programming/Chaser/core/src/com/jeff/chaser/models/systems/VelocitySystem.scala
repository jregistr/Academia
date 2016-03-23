package com.jeff.chaser.models.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.jeff.chaser.models.components.{TransformComponent, VelocityComponent}


class VelocitySystem extends IteratingSystem(Family.all(classOf[VelocityComponent], classOf[TransformComponent]).get()) {

  private val v= ComponentMapper.getFor(classOf[VelocityComponent])
  private val t = ComponentMapper.getFor(classOf[TransformComponent])

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val trans = t.get(entity)
    val vel = v.get(entity)
    trans.position.add(vel.velocity)
  }
}
