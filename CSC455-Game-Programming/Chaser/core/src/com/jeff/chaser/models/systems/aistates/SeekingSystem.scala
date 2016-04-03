package com.jeff.chaser.models.systems.aistates

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.{MathUtils, Vector2}
import com.jeff.chaser.models.components.ai.states.{PatrolComponent, SeekingComponent}
import com.jeff.chaser.models.graph.Graph
import com.jeff.chaser.util.Constants._


class SeekingSystem extends AiStateSystem(classOf[SeekingComponent]) {

  val minX = PATROL_X - (PATROL_X * 0.10f)
  val minY = PATROL_Y - (PATROL_Y * 0.10f)

  val maxX = PATROL_X + (PATROL_X * 0.10f)
  val maxY = PATROL_Y + (PATROL_Y * 0.10f)


  override def checkState(entity: Entity): Unit = {
    if (dcm.get(entity).detectedLastCheck == null) {
      val trans = tm.get(entity)
      if (within(trans.x, trans.y)) {
        val vel = vm.get(entity)
        vel.targetX = 0
        vel.targetY = 0
        entity.remove(classOf[SeekingComponent])
        entity.add(new PatrolComponent)
      }
    }
  }

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val seekerTrans = tm.get(entity)

    val seekerVelo = vm.get(entity)

    val detectedLoc = getDestination(entity)
    val pathObject = Graph.findPath((seekerTrans.x, seekerTrans.y), (detectedLoc.x, detectedLoc.y))
    if (pathObject != null && pathObject.path.size > 0) {
      val path = pathObject.path

      path.set(path.size - 1, (seekerTrans.x, seekerTrans.y))

      val curVelocity = new Vector2(seekerVelo.x, seekerVelo.y)

      val desiredVelocity = new Vector2(detectedLoc.x, detectedLoc.y)
        .sub(new Vector2(seekerTrans.x, seekerTrans.y))
        .nor().scl(seekerVelo.maxX)

      val steering = desiredVelocity.cpy()
      steering.sub(curVelocity)

      steering.scl(90 * deltaTime)

      curVelocity.add(steering)
      val x = curVelocity.x
      val y = curVelocity.y

      curVelocity.nor()
      val angle = MathUtils.atan2(curVelocity.y, curVelocity.x) * MathUtils.radDeg

      seekerVelo.targetX = x
      seekerVelo.targetY = y
      seekerTrans.rotation = angle
    }

  }

  private def getDestination(detector: Entity): Vector2 = {
    val seekerDetector = dcm.get(detector)
    seekerDetector.detectedLastCheck match {
      case null =>
        new Vector2(PATROL_X, PATROL_Y)
      case _ =>
        val trans = tm.get(seekerDetector.detectedLastCheck)
        new Vector2(trans.x, trans.y)
    }
  }

  private def within(x: Float, y: Float): Boolean = x >= minX && x <= maxX && y >= minY && y <= maxY

}
