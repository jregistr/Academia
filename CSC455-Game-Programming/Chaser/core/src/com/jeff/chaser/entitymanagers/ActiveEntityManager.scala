package com.jeff.chaser.entitymanagers

import com.badlogic.ashley.core.{Engine, Entity, Family}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.utils.{Array => LibArray, ObjectMap}
import com.jeff.chaser.models.components._
import com.jeff.chaser.models.systems.{AnimatorSystem, AccelerationSystem, VelocitySystem}
import com.jeff.chaser.models.util.{AnimInfo, AccelerationState, Tag}
import com.jeff.chaser.util.Constants.TexConstants.{TANKS, TANKS_NUM_LINES, TANKS_PER_LINE, grab}


class ActiveEntityManager(engine: Engine, textures: Map[String, Texture]) extends EntityManager(engine,
  Family.all(classOf[TransformComponent], classOf[RenderComponent], classOf[NonStaticComponent]) get()) {

  {
    val w = Gdx.graphics.getWidth
    val h = Gdx.graphics.getHeight
    val tankSheet = grab(TANKS, textures)
    val sheetSplit = TextureRegion.split(tankSheet,
      tankSheet.getWidth / TANKS_PER_LINE,
      tankSheet.getHeight / TANKS_NUM_LINES)
    val init = "State"

    def getLine(line: Int): (TextureRegion, LibArray[TextureRegion]) = {
      val array = sheetSplit(line)
      val libArray = new LibArray[TextureRegion](TANKS_PER_LINE)
      array.foreach(libArray.add)
      (array(0), libArray)
    }

    def simpleState(regions: LibArray[TextureRegion]): ObjectMap[Object, AnimInfo] = {
      val map = new ObjectMap[Object, AnimInfo]()
      map.put(init, new AnimInfo(new Animation(1.0f / 10, regions, PlayMode.LOOP)))
      map
    }

    def makeTankEntity(name: String, tag: Tag.Value,
                       basePos: (Float, Float), veloMax: (Float, Float),
                       acc: (Float, Float), baseTex: TextureRegion,
                       animRegions: LibArray[TextureRegion], rotation: Float): Entity = {
      val entity = new Entity
      entity.add(new IdentityComponent(name, tag))
      entity.add(new NonStaticComponent)
      entity.add(new TransformComponent(basePos._1 - (baseTex.getRegionWidth / 2.0f),
        basePos._2 - (baseTex.getRegionHeight / 2.0f), rotation))
      entity.add(new VelocityComponent(0, 0, veloMax._1, veloMax._2))
      entity.add(new AccelerationComponent(acc._1, acc._2, AccelerationState.ZERO))
      entity.add(new RenderComponent(baseTex, baseTex.getRegionWidth,
        baseTex.getRegionHeight))
      entity.add(new AnimatorComponent(init, init, simpleState(animRegions)))
      entity
    }

    val guardLine = getLine(1)
    val guard = makeTankEntity("Player", Tag.PLAYER, (w * 0.85f, h * 0.85f),
      (100f, 100f), (10f, 6f), guardLine._1, guardLine._2, 180)

    val playerLine = getLine(0)
    val player = makeTankEntity("Cool Guard", Tag.GUARD, (w * 0.2f, h * 0.2f),
      (150f, 150f), (25f, 20f), playerLine._1, playerLine._2, 0f)
    engine.addEntity(guard)
    engine.addEntity(player)
    addSystems()
  }

  private def addSystems(): Unit ={
    engine.addSystem(new VelocitySystem)
    engine.addSystem(new AccelerationSystem)
    engine.addSystem(new AnimatorSystem)
  }

  def updateKeyQueue(keyCode: Int, up: Boolean): Unit = {

  }

  override def update(delta: Float): Unit = {

  }

  override def close(): Unit = {

  }

}
