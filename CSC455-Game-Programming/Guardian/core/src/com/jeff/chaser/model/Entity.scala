package com.jeff.chaser.model

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.Body
import com.jeff.chaser.util.UserData


abstract class Entity(val name:String, val body: Body, var texture:Texture) {

  def userData():UserData
  body.setUserData(userData())

}
