package com.jeff.chaser.entitymanagers

import com.badlogic.ashley.core._
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, TextureRegion}
import com.badlogic.gdx.math.Vector2
import com.jeff.chaser.models.components.{RenderComponent, TransformComponent}
import com.jeff.chaser.util.Constants.TexConstants.{GROUND, HOUSE, grab}

class StaticEntityManager(engine: Engine, textures: Map[String, Texture]) extends EntityManager(engine) {

  private val batch = new SpriteBatch()

  private val tm = ComponentMapper.getFor(classOf[TransformComponent])
  private val rm = ComponentMapper.getFor(classOf[RenderComponent])

  private var entities: ImmutableArray[Entity] = {
    val family = Family.all(classOf[TransformComponent], classOf[RenderComponent]).get()
    def get() = engine.getEntitiesFor(family)
    val temp = get()

    engine.addEntityListener(family, new EntityListener {
      override def entityAdded(entity: Entity): Unit = {
        entities = get()
      }

      override def entityRemoved(entity: Entity): Unit = {
        get()
      }
    })
    temp
  }

  {
    val w = Gdx.graphics.getWidth.toFloat
    val h = Gdx.graphics.getHeight.toFloat

    val groundTex = new TextureRegion(grab(GROUND, textures))
    val houseTex = new TextureRegion(grab(HOUSE, textures))

    val ground = new Entity()
    ground.add(new TransformComponent(new Vector2(w / 2.0f - (groundTex.getRegionWidth / 2.0f),
      h / 2.0f - (groundTex.getRegionHeight / 2.0f))))
    ground.add(new RenderComponent(groundTex, groundTex.getRegionWidth, groundTex.getRegionHeight,
      1, 1, Color.WHITE))

    val house = new Entity()
    house.add(new TransformComponent(new Vector2(w * 0.9f, h * 0.9f)))
    house.add(new RenderComponent(houseTex, houseTex.getRegionWidth, houseTex.getRegionHeight, 1, 1, Color.WHITE))

    engine.addEntity(ground)
    engine.addEntity(house)
  }

  override def update(delta: Float): Unit = {

  }

  override def draw(delta: Float): Unit = {
    val iter = entities.iterator()
    batch.begin()
    while (iter.hasNext) {
      val entity = iter.next()
      val t = tm.get(entity)
      val r = rm.get(entity)
      val p = t.position
      batch.draw(r.tex, p.x, p.y, r.oX, r.oY, r.initWidth, r.initHeight, r.sX, r.sY, 0)
    }
    batch.end()
  }

  override def close(): Unit = {
    batch.dispose()
  }
}
