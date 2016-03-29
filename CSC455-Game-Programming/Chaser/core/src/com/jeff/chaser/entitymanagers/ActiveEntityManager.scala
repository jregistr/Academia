package com.jeff.chaser.entitymanagers

import com.badlogic.ashley.core.{ComponentMapper, Engine, Entity, Family}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.graphics.{OrthographicCamera, Texture}
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.{Array => LibArray, ObjectMap}
import com.jeff.chaser.models.components.ai.{DetectionComponent, DetectorComponent}
import com.jeff.chaser.models.components.motion.{AccelerationComponent, TransformComponent, VelocityComponent}
import com.jeff.chaser.models.components.util.{AttachedComponent, ControlledComponent, IdentityComponent, NonStaticComponent}
import com.jeff.chaser.models.components.view.{DetectorConeComponent, AnimatorComponent, RenderComponent}
import com.jeff.chaser.models.systems._
import com.jeff.chaser.models.util.{AnimInfo, Tag}
import com.jeff.chaser.util.Constants.TexConstants.{TANKS, TANKS_NUM_LINES, TANKS_PER_LINE, grab}


class ActiveEntityManager(val camera: OrthographicCamera, engine: Engine, textures: Map[String, Texture])
  extends EntityManager(engine,
    Family.all(classOf[TransformComponent], classOf[RenderComponent], classOf[NonStaticComponent]) get()) {

  private val keyState = new ObjectMap[Int, Boolean]()
  keyState.put(Keys.S, false)
  keyState.put(Keys.W, false)

  //Systems that are held here
  private val controlSystem = new ControlSystem

  private val mousePos = new Vector3

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
      entity.add(new AccelerationComponent(acc._1, acc._2))
      entity.add(new RenderComponent(baseTex, baseTex.getRegionWidth,
        baseTex.getRegionHeight))
      entity.add(new AnimatorComponent(init, init, simpleState(animRegions)))
      entity
    }

    val guardLine = getLine(1)
    val guard = makeTankEntity("Player", Tag.PLAYER, (w * 0.85f, h * 0.85f),
      (150f, 120f), (90f, 80f), guardLine._1, guardLine._2, 180)
    val detecFov = 120f
    val detecRange = 200f
    val detectorOffset = (
      detecRange - (guardLine._1.getRegionWidth / 2.0f),
      (-detecRange) + (guardLine._1.getRegionHeight / 2.0f)
      )
    val detectMake = DetectorViewSystem.makeDetectorCone(detecRange, detecFov)
    val guardCone = new DetectorComponent(detecFov, detecRange, detectorOffset._1, detectorOffset._2, detectMake._2)
    guard.add(guardCone)

    val playerLine = getLine(0)
    val player = makeTankEntity("Cool Guard", Tag.GUARD, (w * 0.2f, h * 0.2f),
      (200f, 150f), (110f, 95f), playerLine._1, playerLine._2, 0f)
    player.add(new ControlledComponent)
    val poly = DetectorViewSystem.makeDetectableRect(
      playerLine._1.getRegionWidth,
      playerLine._1.getRegionHeight
    )
    poly.setOrigin(playerLine._1.getRegionWidth / 2.0f, playerLine._1.getRegionHeight / 2.0f)
    player.add(new DetectionComponent(poly))

    val detectorCone = new Entity
    detectorCone.add(new NonStaticComponent)
    detectorCone.add(new DetectorConeComponent)
    detectorCone.add(new TransformComponent(0, 0))

    val cone = detectMake._1
    val coneRender = new RenderComponent(cone, cone.getRegionWidth, cone.getRegionHeight)
    val value = -Math.abs(detectorOffset._1 / 2.0f) - 2
    coneRender.oX = value
    detectMake._2.setOrigin(value, detectMake._2.getOriginY)

    detectorCone.add(coneRender)
    detectorCone.add(new AttachedComponent(guard,
      detectorOffset._1,
      detectorOffset._2))

    engine.addEntity(guard)
    engine.addEntity(player)
    engine.addEntity(detectorCone)

    addSystems()
  }

  private def addSystems(): Unit = {
    engine.addSystem(controlSystem)
    engine.addSystem(new VelocitySystem)
    engine.addSystem(new AccelerationSystem)
    engine.addSystem(new AnimatorSystem)
    engine.addSystem(new AttachedSystem)
    engine.addSystem(new DetectorSystem)
  }

  def updateKeyQueue(keyCode: Int, down: Boolean): Unit = {
    keyCode match {
      case Keys.S | Keys.W => keyState.put(keyCode, down)
      case _ =>
    }
  }

  override def update(delta: Float): Unit = {
    val x = Gdx.input.getX
    val y = Gdx.input.getY
    mousePos.set(x, y, 0)
    camera.unproject(mousePos)
    controlSystem.updateKeyStates(
      keyState.get(Keys.W),
      keyState.get(Keys.S),
      mousePos
    )
  }

  override def close(): Unit = {
    batch.dispose()
    val fam = Family.all(classOf[DetectorConeComponent], classOf[RenderComponent]).get()
    val entities = engine.getEntitiesFor(fam)
    val iter = entities.iterator()
    while (iter.hasNext) {
      val ent = iter.next()
      rm.get(ent).tex.getTexture.dispose()
    }
  }

}
