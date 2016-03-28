package com.jeff.chaser.models.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.jeff.chaser.models.components.ai.DetectorComponent
import com.jeff.chaser.models.components.motion.TransformComponent


class DetectorSystem extends IteratingSystem(Family.all(classOf[DetectorComponent], classOf[TransformComponent]).get()) {

  private val dm = ComponentMapper.getFor(classOf[DetectorComponent])
  private val tm = ComponentMapper.getFor(classOf[TransformComponent])

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val d = dm.get(entity)
    val t = tm.get(entity)
    val vec = new Vector2(t.x + d.oX, t.y + d.oY)
    println(s"B4:(${vec.x},${vec.y})")
    vec.setAngle(t.rotation)
    println(s"AF:(${vec.x},${vec.y})")
    println(s"MORE:(${vec.x - (Gdx.graphics.getWidth / 2.0f)},${vec.y - (Gdx.graphics.getHeight / 2.0f)})")
  }
}
