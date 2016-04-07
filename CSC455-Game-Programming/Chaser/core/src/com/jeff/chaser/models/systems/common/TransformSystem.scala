package com.jeff.chaser.models.systems.common

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.jeff.chaser.models.components.ai.detect.DetectionComponent
import com.jeff.chaser.models.components.motion.TransformComponent
import com.jeff.chaser.util.Constants


class TransformSystem extends IteratingSystem(Family.all(classOf[TransformComponent], classOf[DetectionComponent]).get()) {

  private val tm = ComponentMapper.getFor(classOf[TransformComponent])

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val trans = tm.get(entity)
    if (trans.x >= Constants.HOUSE_X - 50 && trans.x <= Constants.HOUSE_X + Constants.HOUSE_WIDTH) {
      if (trans.y >= Constants.HOUSE_Y - 50 && trans.y <= Constants.HOUSE_Y + Constants.HOUSE_HEIGHT) {
        trans.x -= 80
        trans.y -= 50
      }
    }
  }
}
