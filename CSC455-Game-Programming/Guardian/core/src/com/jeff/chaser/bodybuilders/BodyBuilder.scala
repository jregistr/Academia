package com.jeff.chaser.bodybuilders

import com.badlogic.gdx.math.{MathUtils, Vector2}
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.{BodyDef, FixtureDef, PolygonShape}

import scala.collection.mutable.ArrayBuffer


object BodyBuilder {

  def squareBodyDefs(x: Float, y: Float, w: Float, h: Float, static: Boolean): (FixtureDef, BodyDef) = {
    val fixtureDef = new FixtureDef
    val bodyDef = new BodyDef

    static match {
      case false =>
        bodyDef.fixedRotation = false
        bodyDef.linearDamping = 10.0f
        bodyDef.`type` = BodyType.DynamicBody
      case _ =>
        bodyDef.fixedRotation = true
        bodyDef.`type` = BodyType.StaticBody
    }

    bodyDef.position.set(x, y)

    val rec = new PolygonShape()
    rec.setAsBox(w, h)
    fixtureDef.shape = rec
    fixtureDef.density = 1.0f
    (fixtureDef, bodyDef)
  }

  def arrowFixture(w: Float, h: Float): FixtureDef = {
    val fixtureDef = new FixtureDef

    val triBuffer = new ArrayBuffer[Vector2]()
    triBuffer ++= Seq(
      new Vector2(w, -h / 2.0f),
      new Vector2(w * 1.3f, 0),
      new Vector2(w, h / 2.0f)
    )
    val shape = new PolygonShape()
    shape.set(triBuffer.toArray)
    fixtureDef.shape = shape
    fixtureDef
  }

  def makeConeDefs(x: Float, y: Float, radius: Float, len: Float): (BodyDef, FixtureDef) = {
    val bodyDef = new BodyDef
    val fixtureDef = new FixtureDef

    val cone = new PolygonShape()
    val verticies = new ArrayBuffer[Vector2]()
    verticies += new Vector2(radius, 0)
    val res = 6 //FOV_DELTA
    val step = ((res / 2) / (res / 2)) * MathUtils.degreesToRadians
    val angle = (90 - (res / 2)) * MathUtils.degreesToRadians


    // Create the shape
    /* PolygonShape triangle = new PolygonShape();
     Vector2[] vertices = new Vector2[(int) FOV_DELTA + 2];
     vertices[0] = new Vector2(CIRCLE_RADIUS, 0f);
     float angleStep = ((FOV_ANGLE / 2) / (FOV_DELTA / 2)) * MathUtils.degreesToRadians;
     float angle = (90 - (FOV_ANGLE / 2)) * MathUtils.degreesToRadians;
     for(int i = 1; i < FOV_DELTA + 2; i++) {
       vertices[i] = new Vector2((float) (FOV_DISTANCE * Math.sin(angle)), (float) (FOV_DISTANCE * Math.cos(angle)));
       angle += angleStep;
     }
     triangle.set(vertices);*/


    (bodyDef, fixtureDef)
  }

}
