package com.jeff.chaser.entitymanagers

import com.badlogic.ashley.core.{Engine, Family}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.{OrthographicCamera, Texture}
import com.badlogic.gdx.utils.ObjectMap
import com.jeff.chaser.builders.EntityBuilder
import com.jeff.chaser.models.components.ai.detect.{DetectionComponent, DetectorComponent}
import com.jeff.chaser.models.components.motion.TransformComponent
import com.jeff.chaser.models.components.util._
import com.jeff.chaser.models.components.view.{DetectorConeComponent, RenderComponent}
import com.jeff.chaser.models.systems.common._
import com.jeff.chaser.models.util.Tag
import com.jeff.chaser.util.Constants
import com.jeff.chaser.util.Constants.TexConstants.{TANKS, grab}
import com.jeff.chaser.util.dsl.ExtendEngine._
import com.jeff.chaser.util.dsl.ExtendEntity._


class ActiveEntityManager(val camera: OrthographicCamera, engine: Engine, textures: Map[String, Texture])
  extends EntityManager(engine,
    Family.all(classOf[TransformComponent], classOf[RenderComponent], classOf[NonStaticComponent]) get()) {

  private val keyState = new ObjectMap[Int, Boolean]()
  keyState.put(Keys.S, false)
  keyState.put(Keys.W, false)

  //Systems that are held here
  private val controlSystem = new ControlSystem

  {
    val w = Gdx.graphics.getWidth
    val h = Gdx.graphics.getHeight
    val tankSheet = Constants.splitTankSheet(grab(TANKS, textures))
    val greenTankLine = Constants.getLineFromSheet(tankSheet, 0)
    val dfov = 120
    val ddist = 200

    val redTankLine = Constants.getLineFromSheet(tankSheet, 1)
    val rgt = redTankLine.first()

    val player = EntityBuilder.makeTankEntity("Player",
      Tag.PLAYER, (w * 0.2f, h * 0.15f),
      (200f, 200f), (100f, 100f),
      greenTankLine.first(),
      greenTankLine, 0)
    player ++= Seq(new ControlledComponent, new DetectionComponent)

    val guard = EntityBuilder.makeTankEntity("Guard",
      Tag.PLAYER, (w * 0.7f, h * 0.75f),
      (170, 170), (90, 90),
      rgt, redTankLine, 180)
    guard.add(new DetectorComponent(dfov, ddist, 0, 0))

    val cone = EntityBuilder.makeAttachedDetector(ddist, dfov, 0, 0,
      rgt.getRegionWidth, rgt.getRegionHeight, guard)

    engine += Seq(
      player,
      cone,
      guard
    )

    addSystems()
  }

  private def addSystems(): Unit = {
    engine ++= Seq(
      new VelocitySystem,
      new AccelerationSystem,
      new AnimatorSystem,
      new AttachedSystem,
      new DetectorSystem,
      controlSystem
    )
  }

  def updateKeyQueue(keyCode: Int, down: Boolean): Unit = {
    keyCode match {
      case Keys.A | Keys.S | Keys.D | Keys.W => keyState.put(keyCode, down)
      case _ =>
    }
  }

  override def update(delta: Float): Unit = {
    controlSystem.updateKeyStates(
      keyState.get(Keys.A),
      keyState.get(Keys.S),
      keyState.get(Keys.D),
      keyState.get(Keys.W)
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
