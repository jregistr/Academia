package com.jeff.chaser.entitymanagers

import com.badlogic.ashley.core.{ComponentMapper, Engine}
import com.jeff.chaser.models.components.{RenderComponent, TransformComponent}


class ActiveEntityManager(engine: Engine) extends EntityManager(engine) {

  private val rm = ComponentMapper.getFor(classOf[RenderComponent])
  private val tm = ComponentMapper.getFor(classOf[TransformComponent])

  {

  }

  def update(delta: Float): Unit = {

  }

  def draw(delta: Float): Unit = {

  }

  override def close(): Unit = {

  }
}
