package com.jeff.chaser

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera, Texture}
import com.badlogic.gdx.math.{Matrix4, Vector2}
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.{ApplicationAdapter, Gdx, InputProcessor}
import com.jeff.chaser.bodybuilders.BodyBuilder
import com.jeff.chaser.util.Constants
import com.jeff.chaser.util.Constants.TexConstants._

class Chaser extends ApplicationAdapter with InputProcessor {

  private val asset = new AssetManager()
  private var camera: OrthographicCamera = _
  private var world: World = _
  private var debugRender:Box2DDebugRenderer = _
  private var batch:SpriteBatch = _
  private var debugMatrix:Matrix4 = _

  override def create() {
    asset.load(GROUND, classOf[Texture])
    asset.load(HOUSE, classOf[Texture])
    asset.load(TANKS, classOf[Texture])
    asset.finishLoading()

    camera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    camera.setToOrtho(false, Gdx.graphics.getWidth, Gdx.graphics.getHeight)

    world = new World(new Vector2(0, 0), false)
    debugRender = new Box2DDebugRenderer()
    batch = new SpriteBatch()
    debugMatrix = new Matrix4(camera.combined)
    debugMatrix.scale(Constants.M_TO_PIX, Constants.M_TO_PIX,1f)

    val houseDefs = BodyBuilder.squareBodyDefs(60,25, 7, 4, static = true)
    world.createBody(houseDefs._2).createFixture(houseDefs._1)

    val point = BodyBuilder.arrowFixture(5, 2.5f)
    val playerTankDefs = BodyBuilder.squareBodyDefs(10, 10, 5, 2.5f, static = false)
    val player = world.createBody(playerTankDefs._2)
    player.createFixture(playerTankDefs._1)
    player.createFixture(point)


  }

  override def render() {
    update()
    draw()
  }

  private def update(): Unit = {
    world.step(1 / 60f, 10, 8)
  }

  private def draw(): Unit = {
    Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    debugRender.render(world, debugMatrix)
  }

  override def dispose(): Unit = {
    asset.dispose()
  }

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = false

  override def keyTyped(character: Char): Boolean = false

  override def keyDown(keycode: Int): Boolean = {

    false
  }

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  override def keyUp(keycode: Int): Boolean = {

    true
  }

  override def scrolled(amount: Int): Boolean = false

  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false
}