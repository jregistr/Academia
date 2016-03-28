package com.jeff.chaser.models.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.jeff.chaser.models.components.ai.DetectorComponent
import com.jeff.chaser.models.components.motion.TransformComponent


class DetectorSystem extends IteratingSystem(Family.all(classOf[DetectorComponent], classOf[TransformComponent]).get()) {

  private val dm = ComponentMapper.getFor(classOf[DetectorComponent])
  private val tm = ComponentMapper.getFor(classOf[TransformComponent])

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {

  }
}
