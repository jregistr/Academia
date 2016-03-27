package com.jeff.chaser.entitymanagers

import com.badlogic.ashley.core.{ComponentMapper, Engine, Entity, Family}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.graphics.{OrthographicCamera, Texture}
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.{Array => LibArray, ObjectMap}
import com.jeff.chaser.models.components.ai.DetectorComponent
import com.jeff.chaser.models.components.motion.{AccelerationComponent, TransformComponent, VelocityComponent}
import com.jeff.chaser.models.components.util.{AttachedComponent, ControlledComponent, IdentityComponent, NonStaticComponent}
import com.jeff.chaser.models.components.view.{SimpleTextureComponent, AnimatorComponent, RenderTextureComponent}
import com.jeff.chaser.models.systems._
import com.jeff.chaser.models.util.{AnimInfo, Tag}
import com.jeff.chaser.util.Constants.TexConstants.{TANKS, TANKS_NUM_LINES, TANKS_PER_LINE, grab}


class ActiveEntityManager(val camera: OrthographicCamera, engine: Engine, textures: Map[String, Texture])
  extends EntityManager(engine,
    Family.all(classOf[TransformComponent], classOf[RenderTextureComponent], classOf[NonStaticComponent]) get()) {

  private val keyState = new ObjectMap[Int, Boolean]()
  keyState.put(Keys.A, false)
  keyState.put(Keys.S, false)
  keyState.put(Keys.D, false)
  keyState.put(Keys.W, false)

  //Systems that are held here
  private val controlSystem = new ControlSystem

  private val mousePos = new Vector3

  private val otherViewFam = Family.all(classOf[SimpleTextureComponent]).get()
  private val sm = ComponentMapper.getFor(classOf[SimpleTextureComponent])

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
      entity.add(new RenderTextureComponent(baseTex, baseTex.getRegionWidth,
        baseTex.getRegionHeight))
      entity.add(new AnimatorComponent(init, init, simpleState(animRegions)))
      entity
    }

    val guardLine = getLine(1)
    val guard = makeTankEntity("Player", Tag.PLAYER, (w * 0.85f, h * 0.85f),
      (150f, 120f), (90f, 80f), guardLine._1, guardLine._2, 180)
    val guardCone = new DetectorComponent(180, 200)
    guard.add(guardCone)

    val playerLine = getLine(0)
    val player = makeTankEntity("Cool Guard", Tag.GUARD, (w * 0.2f, h * 0.2f),
      (200f, 150f), (110f, 95f), playerLine._1, playerLine._2, 0f)
    player.add(new ControlledComponent)


    val detectorCone = new Entity
    detectorCone.add(new NonStaticComponent)
    detectorCone.add(new SimpleTextureComponent(DetectorViewSystem.makeDetectorCone(guardCone, 1000)))
    detectorCone.add(new AttachedComponent(Family.all(classOf[TransformComponent]).get(), guard))

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
  }

  def updateKeyQueue(keyCode: Int, down: Boolean): Unit = {
    keyCode match {
      case Keys.A | Keys.S | Keys.D | Keys.W => keyState.put(keyCode, down)
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

  override def draw(): Unit = {
    super.draw()
    //draw shape stuff
    batch.begin()
    val entities = engine.getEntitiesFor(otherViewFam)
    val iter = entities.iterator()
    while (iter.hasNext) {
      val tex = sm.get(iter.next()).tex
      batch.draw(tex, 0, 0)
    }
    batch.end()
  }

  override def close(): Unit = {

  }

}
