package com.jeff.chaser.entitymanagers

import com.badlogic.ashley.core._
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.jeff.chaser.models.components.{RenderComponent, StaticComponent, TransformComponent}
import com.jeff.chaser.util.Constants.TexConstants.{GROUND, HOUSE, grab}

class StaticEntityManager(engine: Engine, textures: Map[String, Texture]) extends EntityManager(engine,
  Family.all(classOf[TransformComponent], classOf[RenderComponent], classOf[StaticComponent]) get()) {

  {
    val w = Gdx.graphics.getWidth.toFloat
    val h = Gdx.graphics.getHeight.toFloat

    val groundTex = new TextureRegion(grab(GROUND, textures))
    val houseTex = new TextureRegion(grab(HOUSE, textures))

    val staticComponent = new StaticComponent

    val ground = new Entity()
    ground.add(new TransformComponent((w / 2.0f) - (groundTex.getRegionWidth / 2.0f),
      (h / 2.0f) - (groundTex.getRegionHeight / 2.0f)))
    ground.add(new RenderComponent(groundTex, groundTex.getRegionWidth, groundTex.getRegionHeight))
    ground.add(staticComponent)

    val house = new Entity()
    house.add(new TransformComponent(w * 0.95f, h * 0.75f))
    house.add(new RenderComponent(houseTex, houseTex.getRegionWidth, houseTex.getRegionHeight))
    house.add(staticComponent)

    engine.addEntity(ground)
    engine.addEntity(house)
  }

  override def update(delta: Float): Unit = {

  }

  override def close(): Unit = {
    batch.dispose()
  }
}
