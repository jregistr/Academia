package com.jeff.chaser.entitymanagers

import com.badlogic.ashley.core.Engine


abstract class EntityManager(protected val engine: Engine) {

  def update(delta: Float): Unit

  def draw(delta: Float): Unit

  def close():Unit

}
