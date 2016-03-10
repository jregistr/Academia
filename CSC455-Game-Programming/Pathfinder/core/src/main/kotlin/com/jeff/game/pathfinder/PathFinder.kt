package com.jeff.game.pathfinder

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.OrthographicCamera
import com.jeff.game.pathfinder.scenes.GraphScene
import com.jeff.game.pathfinder.util.option.None
import com.jeff.game.pathfinder.util.option.Option
import com.jeff.game.pathfinder.util.option.Some


class PathFinder : Game(), InputProcessor {

    private var optScene: Option<GraphScene> = None()

    override fun create() {
        val camera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        camera.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        optScene = Some(GraphScene("bigmap.txt", camera))
        setScreen(optScene.get())
        Gdx.input.inputProcessor = this
    }

    override fun touchUp(p0: Int, p1: Int, p2: Int, p3: Int): Boolean {
        return true
    }

    override fun mouseMoved(p0: Int, p1: Int): Boolean {
        return true
    }

    override fun keyTyped(p0: Char): Boolean {
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (optScene.defined()) {
            optScene.get().clicked(screenX, screenY)
        }
        return true
    }

    override fun scrolled(p0: Int): Boolean {
        return true
    }

    override fun keyUp(p0: Int): Boolean {
        return true
    }

    override fun touchDragged(p0: Int, p1: Int, p2: Int): Boolean {
        return true
    }

    override fun keyDown(p0: Int): Boolean {
        return true
    }

}