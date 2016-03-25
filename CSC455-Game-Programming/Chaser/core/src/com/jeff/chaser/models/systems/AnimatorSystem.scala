package com.jeff.chaser.models.systems

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.jeff.chaser.models.components.{AnimatorComponent, RenderComponent}
import com.jeff.chaser.models.util.AnimInfo


class AnimatorSystem extends IteratingSystem(Family.all(classOf[AnimatorComponent], classOf[RenderComponent]).get()) {

  private val am = ComponentMapper.getFor(classOf[AnimatorComponent])
  private val rm = ComponentMapper.getFor(classOf[RenderComponent])

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val animComp = am.get(entity)
    val renderComp = rm.get(entity)

    if (animComp.curState != animComp.nextState) {
      animComp.states.get(animComp.curState).elapsed = 0
      animComp.curState = animComp.nextState
    }

    val animInfo: AnimInfo = animComp.states.get(animComp.curState)
    if (animInfo.animation.isAnimationFinished(animInfo.elapsed)) {
      animInfo.elapsed = 0
    }

    val frame = animInfo.animation.getKeyFrame(animInfo.elapsed, animInfo.animation.getPlayMode match {
      case PlayMode.LOOP | PlayMode.LOOP_PINGPONG | PlayMode.LOOP_RANDOM | PlayMode.LOOP_REVERSED => true
      case _ => false
    })
    renderComp.tex = frame
    animInfo.elapsed += deltaTime
  }

}
