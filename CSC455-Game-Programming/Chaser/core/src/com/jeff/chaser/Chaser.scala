package com.jeff.chaser

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera, Texture}
import com.badlogic.gdx.{ApplicationAdapter, Gdx, InputProcessor}
import com.jeff.chaser.entitymanagers.{ActiveEntityManager, StaticEntityManager}
import com.jeff.chaser.util.Constants.TexConstants._

class Chaser extends ApplicationAdapter with InputProcessor {

  private val asset = new AssetManager()
  private var engine: Engine = _
  private var statics: StaticEntityManager = _
  private var actives: ActiveEntityManager = _
  private var camera: OrthographicCamera = _

  override def create() {
    asset.load(GROUND, classOf[Texture])
    asset.load(HOUSE, classOf[Texture])
    asset.load(TANKS, classOf[Texture])
    asset.finishLoading()

    camera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    camera.setToOrtho(false, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    engine = new Engine()
    statics = new StaticEntityManager(engine, Map(
      GROUND -> asset.get(GROUND, classOf[Texture]),
      HOUSE -> asset.get(HOUSE, classOf[Texture])
    ))

    actives = new ActiveEntityManager(camera, engine, Map(
      TANKS -> asset.get(TANKS, classOf[Texture])
    ))
    Gdx.input.setInputProcessor(this)
  }

  override def render() {
    val delta = Gdx.graphics.getDeltaTime
    actives.update(delta)
    engine.update(delta)
    Gdx.gl.glClearColor(1, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    statics.draw()
    actives.draw()
  }

  override def dispose(): Unit = {
    statics.close()
    actives.close()
    asset.dispose()
  }

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = false

  override def keyTyped(character: Char): Boolean = false

  override def keyDown(keycode: Int): Boolean = {
    actives.updateKeyQueue(keycode, down = true)
    false
  }

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  override def keyUp(keycode: Int): Boolean = {
    actives.updateKeyQueue(keycode, down = false)
    true
  }

  override def scrolled(amount: Int): Boolean = false

  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false
}