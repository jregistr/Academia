package com.jeff.chaser.models.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.{MathUtils, Vector2, Vector3}
import com.jeff.chaser.models.components.motion.{TransformComponent, VelocityComponent}
import com.jeff.chaser.models.components.util.{NonStaticComponent, ControlledComponent}


class ControlSystem extends IteratingSystem(Family.all(classOf[ControlledComponent],
  classOf[TransformComponent], classOf[VelocityComponent], classOf[NonStaticComponent]).get()) {

  private val tm = ComponentMapper.getFor(classOf[TransformComponent])
  private val vm = ComponentMapper.getFor(classOf[VelocityComponent])
  private var mX = 0f
  private var mY = 0f

  private var throttle = 0

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val t = tm.get(entity)
    val v = vm.get(entity)
    var angle = MathUtils.atan2(
      mY - t.y,
      mX - t.x
    )
    angle *= (180.0f / MathUtils.PI)
    if (angle < 0) {
      angle = 360 - (-angle)
    }
    val end = angle
    t.rotation = end

    val x = MathUtils.cosDeg(end)
    val y = MathUtils.sinDeg(end)
    val vec = new Vector2(x, y)
    vec.nor()

    vec.scl(v.maxX)
    v.targetX = vec.x * throttle
    v.targetY = vec.y * throttle
  }

  def updateKeyStates(w: Boolean, s: Boolean, mousePos: Vector3): Unit = {
    mX = mousePos.x
    mY = mousePos.y
    if (w) {
      throttle = 1
    } else if (s) {
      throttle = -1
    } else {
      throttle = 0
    }
  }

}
