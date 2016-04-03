package com.jeff.chaser.models.systems.aistates

import com.badlogic.ashley.core._
import com.badlogic.ashley.utils.ImmutableArray
import com.jeff.chaser.models.components.ai.detect.DetectorComponent
import com.jeff.chaser.models.components.ai.states.AiStateComponent
import com.jeff.chaser.models.components.motion.{TransformComponent, VelocityComponent}

import scala.collection.JavaConversions._

/**
  * Abstract class to represent a basic ai state system.
  */
abstract class AiStateSystem[T <: AiStateComponent](aiType: Class[T]) extends EntitySystem {

  protected val tm = ComponentMapper.getFor(classOf[TransformComponent])
  protected val vm = ComponentMapper.getFor(classOf[VelocityComponent])
  protected val aiM = ComponentMapper.getFor(aiType)
  protected val dcm = ComponentMapper.getFor(classOf[DetectorComponent])

  protected var entities: ImmutableArray[Entity] = _

  private val aiFamily = Family.all(aiType, classOf[DetectorComponent], classOf[TransformComponent],
    classOf[VelocityComponent]).get()

  private val listener = new EntityListener {
    override def entityAdded(entity: Entity): Unit = entities = getEngine.getEntitiesFor(aiFamily)

    override def entityRemoved(entity: Entity): Unit = getEngine.getEntitiesFor(aiFamily)
  }

  override def update(deltaTime: Float): Unit = {
    entities.foreach(entity => {
      processEntity(entity, deltaTime)
      checkState(entity)
    })
  }

  override def addedToEngine(engine: Engine): Unit = {
    setProcessing(true)
    entities = engine.getEntitiesFor(aiFamily)
    engine.addEntityListener(aiFamily, listener)
  }

  override def removedFromEngine(engine: Engine): Unit = {
    setProcessing(false)
    entities = null
    engine.removeEntityListener(listener)
  }

  def checkState(entity: Entity): Unit

  def processEntity(entity: Entity, deltaTime: Float): Unit

}
