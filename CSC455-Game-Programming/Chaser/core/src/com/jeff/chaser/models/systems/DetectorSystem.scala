package com.jeff.chaser.models.systems

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.{Polygon, Vector2}
import com.jeff.chaser.models.components.ai.{DetectionComponent, DetectorComponent}
import com.jeff.chaser.models.components.motion.TransformComponent

import scala.collection.mutable.ListBuffer


class DetectorSystem extends EntitySystem {

  setProcessing(false)

  private val detectorFamily = Family.all(classOf[DetectorComponent], classOf[TransformComponent]).get()
  private val detectAbleFamily = Family.all(classOf[DetectionComponent], classOf[TransformComponent]).get()

  private val tm = ComponentMapper.getFor(classOf[TransformComponent])
  private val dm = ComponentMapper.getFor(classOf[DetectorComponent])
  private val dmm = ComponentMapper.getFor(classOf[DetectionComponent])

  private var detectorEntities: ImmutableArray[Entity] = _
  private var detectableEntities: ImmutableArray[Entity] = _

  private def get(family: Family) = getEngine.getEntitiesFor(family)

  override def addedToEngine(engine: Engine): Unit = {
    setProcessing(true)
    detectorEntities = get(detectorFamily)
    detectableEntities = get(detectAbleFamily)

    engine.addEntityListener(detectorFamily, new EntityListener {
      override def entityAdded(entity: Entity): Unit = detectorEntities = get(detectorFamily)

      override def entityRemoved(entity: Entity): Unit = detectorEntities = get(detectorFamily)
    })

    engine.addEntityListener(detectAbleFamily, new EntityListener {
      override def entityAdded(entity: Entity): Unit = detectableEntities = get(detectAbleFamily)

      override def entityRemoved(entity: Entity): Unit = detectableEntities = get(detectAbleFamily)
    })
  }

  override def removedFromEngine(engine: Engine): Unit = {
    setProcessing(false)
    detectorEntities = null
    detectableEntities = null
  }

  override def update(deltaTime: Float): Unit = {
    val iter = detectorEntities.iterator()
    while (iter.hasNext) {
      processEntity(iter.next())
    }
  }

  private def processEntity(entity: Entity): Unit = {
    val t = tm.get(entity)
    val d = dm.get(entity)
    val detectorVec = new Vector2(t.x + d.oX, t.y + d.oY)
    detectorVec.setAngle(t.rotation)

    val detectable = detectableEntities.first()
    val dt = tm.get(detectable)

    val ableVec = new Vector2(dt.x, dt.y)
    ableVec.setAngle(dt.rotation)

    println(detectorVec.add(ableVec).len())

   // println(s"DETECTOR:(${detectorVec.x}, ${detectorVec.y})")
  //  println(s"DETECTABLE:(${ableVec.x}, ${ableVec.y})")
  //  println(s"Angle:${detectorVec.angle(ableVec)}")
    //println(ableVec.dst(detectorVec))

  }

}
