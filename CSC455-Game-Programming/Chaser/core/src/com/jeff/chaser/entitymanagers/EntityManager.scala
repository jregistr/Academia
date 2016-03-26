package com.jeff.chaser.entitymanagers

import com.badlogic.ashley.core._
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.jeff.chaser.models.components.motion.TransformComponent
import com.jeff.chaser.models.components.view.RenderComponent


abstract class EntityManager(protected val engine: Engine, renderFamily: Family) {

  protected val batch = new SpriteBatch()
  protected val rm = ComponentMapper.getFor(classOf[RenderComponent])
  protected val tm = ComponentMapper.getFor(classOf[TransformComponent])

  protected var renderEntities: ImmutableArray[Entity] = {
    def get() = engine.getEntitiesFor(renderFamily)
    val temp = get()

    engine.addEntityListener(renderFamily, new EntityListener {
      override def entityAdded(entity: Entity): Unit = {
        renderEntities = get()
      }

      override def entityRemoved(entity: Entity): Unit = {
        renderEntities = get()
      }
    })
    temp
  }

  def draw(): Unit = {
    val iter = renderEntities.iterator()
    batch.begin()
    while (iter.hasNext) {
      val entity = iter.next()
      val t = tm.get(entity)
      val r = rm.get(entity)
      batch.draw(r.tex, t.x, t.y, r.oX, r.oY, r.initWidth, r.initHeight, r.sX, r.sY, t.rotation)
    }
    batch.end()
  }

  def update(delta: Float): Unit

  def close(): Unit

}
