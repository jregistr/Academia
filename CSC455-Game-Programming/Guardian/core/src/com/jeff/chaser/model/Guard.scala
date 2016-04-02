package com.jeff.chaser.model

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.physics.box2d.Body
import com.jeff.chaser.util.{GuardIdentifier, UserData}

class Guard(name: String, body: Body, texture: Texture) extends Entity(name, body, texture) {
  override def userData(): UserData = GuardIdentifier("Guard")
}
