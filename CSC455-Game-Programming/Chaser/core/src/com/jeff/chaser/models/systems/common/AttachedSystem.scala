package com.jeff.chaser.models.systems.common

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.jeff.chaser.models.components.motion.TransformComponent
import com.jeff.chaser.models.components.util.AttachedComponent


class AttachedSystem extends IteratingSystem(Family.all(classOf[AttachedComponent], classOf[TransformComponent]).get()) {

  private val am = ComponentMapper.getFor(classOf[AttachedComponent])
  private val tm = ComponentMapper.getFor(classOf[TransformComponent])

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val attachedTransform = tm.get(entity)
    val attachedComponent = am.get(entity)

    val toTrans = attachedComponent.entity.getComponent(classOf[TransformComponent])
    attachedTransform.x = toTrans.x + attachedComponent.oX
    attachedTransform.y = toTrans.y + attachedComponent.oY
    attachedTransform.rotation = toTrans.rotation
  }
}
