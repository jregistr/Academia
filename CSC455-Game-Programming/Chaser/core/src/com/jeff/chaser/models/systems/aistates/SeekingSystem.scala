package com.jeff.chaser.models.systems.aistates

import com.badlogic.ashley.core.Entity
import com.jeff.chaser.models.components.ai.states.SeekingComponent


class SeekingSystem extends AiStateSystem(classOf[SeekingComponent]) {

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val seekingComponent = aiM.get(entity)

  }

}
