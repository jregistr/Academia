package com.jeff.chaser.models.systems.common

import com.badlogic.ashley.core._
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2
import com.jeff.chaser.models.components.ai.detect.{DetectionComponent, DetectorComponent}
import com.jeff.chaser.models.components.motion.TransformComponent


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

    val detectable = detectableEntities.first()
    val dt = tm.get(detectable)

    val guardVec = new Vector2(t.x, t.y)
    val playerVec = new Vector2(dt.x, dt.y)
    val distance = guardVec.dst(playerVec)

    val diff = new Vector2(playerVec)
    diff.sub(guardVec).nor()

    val guardOrient = new Vector2(
      Math.cos(Math.toRadians(t.rotation)).toFloat,
      Math.sin(Math.toRadians(t.rotation)).toFloat
    ).nor()

    val dot = guardOrient.dot(diff.nor())
    if (dot >= 0.5f && distance <= (d.distance * 1.25f)) {
      d.detectedLastCheck = detectable
    }

  }

}
