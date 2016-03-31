package com.jeff.chaser.builders


import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.{Array => LibArray, ObjectMap}
import com.jeff.chaser.models.components.motion.{AccelerationComponent, TransformComponent, VelocityComponent}
import com.jeff.chaser.models.components.util.{AttachedComponent, IdentityComponent, NonStaticComponent}
import com.jeff.chaser.models.components.view.{AnimatorComponent, RenderComponent}
import com.jeff.chaser.models.util.{AnimInfo, Tag}
import com.jeff.chaser.util.Constants

import scala.collection.Seq

object EntityBuilder {


  def makeTankEntity(name: String, tag: Tag.Value, basePos: (Float, Float), veloMax: (Float, Float),
                     acc: (Float, Float), baseTex: TextureRegion, animRegions: LibArray[TextureRegion], rotation: Float): Entity = {
    import com.jeff.chaser.util.dsl.ExtendEntity._
    val entity = new Entity
    val w = baseTex.getRegionWidth
    val h = baseTex.getRegionHeight
    val move = "moving"
    val anim = Constants.makeAnim(PlayMode.LOOP, 1f / 10, animRegions)
    val animState = new ObjectMap[Object, AnimInfo]()
    animState.put(move, anim)

    entity ++= Seq(
      new NonStaticComponent,
      new IdentityComponent(name, tag),
      new TransformComponent(
        Constants.centerTex(basePos._1, w),
        Constants.centerTex(basePos._2, h), rotation),
      new VelocityComponent(0, 0, veloMax._1, veloMax._2),
      new AccelerationComponent(acc._1, acc._2),
      new RenderComponent(baseTex, w, h),
      new AnimatorComponent(move, move, animState)
    )
    entity
  }

  def makeAttachedDetector(distance: Float, fov: Float, oX: Float, oY: Float, w: Float, h: Float, attachedTo: Entity): Entity = {
    import com.jeff.chaser.util.dsl.ExtendEntity._
    val entity = new Entity
    val cone = UtilBuilder.makeDetectorCone(distance, fov)
    val render = new RenderComponent(cone, cone.getRegionWidth, cone.getRegionHeight)
    render.oX = 0
    entity ++= Seq(
      new NonStaticComponent,
      new AttachedComponent(attachedTo, (cone.getRegionWidth / 2.0f) - (w / 3f), -(cone.getRegionHeight / 2) + (h / 2.0f)),
      new TransformComponent(100, 100, 0),
      render
    )
    entity
  }

}

