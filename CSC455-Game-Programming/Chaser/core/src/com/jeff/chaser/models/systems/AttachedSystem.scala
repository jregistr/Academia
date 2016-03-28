package com.jeff.chaser.models.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.jeff.chaser.models.components.motion.TransformComponent
import com.jeff.chaser.models.components.util.AttachedComponent


class AttachedSystem extends IteratingSystem(Family.all(classOf[AttachedComponent], classOf[TransformComponent]).get()) {

  private val am = ComponentMapper.getFor(classOf[AttachedComponent])
  private val tm = ComponentMapper.getFor(classOf[TransformComponent])

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val at = tm.get(entity)
    val aa = am.get(entity)

    val pt = tm.get(aa.entity)
    pt match {
      case null => throw new NullPointerException("Missing transform on entity attached to")
      case _ =>
        at.x = pt.x + aa.oX
        at.y = pt.y + aa.oY
        at.rotation = pt.rotation

    }
  }
}
