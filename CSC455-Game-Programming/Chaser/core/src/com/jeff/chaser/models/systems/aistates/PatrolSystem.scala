package com.jeff.chaser.models.systems.aistates

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.MathUtils
import com.jeff.chaser.models.components.ai.states.{PatrolComponent, SeekingComponent}
import com.jeff.chaser.models.components.motion.TransformComponent


class PatrolSystem extends AiStateSystem(classOf[PatrolComponent]) {
  override def checkState(entity: Entity): Unit = {
    val seekerDetector = dcm.get(entity)
    if (seekerDetector.detectedLastCheck != null) {
      entity.remove(classOf[PatrolComponent])
      entity.add(new SeekingComponent)
    }
  }

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val trans = tm.get(entity)
    val patrol = aiM.get(entity)
    if (patrol.timeInState > 5) {
      patrol.timeInState = 0
      patrol.side = patrol.side match {
        case 0 => 120
        case 120 => -120
        case -120 => 120
      }
    }
    rotate(trans, patrol)
    patrol.timeInState += deltaTime
  }

  private def rotate(trans: TransformComponent, patrol: PatrolComponent): Unit = {
    trans.rotation = MathUtils.lerpAngleDeg(trans.rotation, patrol.side, patrol.timeInState / 5)
  }

}
