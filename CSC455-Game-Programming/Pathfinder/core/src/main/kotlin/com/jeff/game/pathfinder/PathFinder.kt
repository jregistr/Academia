package com.jeff.game.pathfinder

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch


class PathFinder : ApplicationAdapter() {

    private lateinit var batch:SpriteBatch
    var img:Texture? = null

    override fun create():Unit {
        batch = SpriteBatch()
        img = Texture("badlogic.jpg")
    }

    override fun render():Unit {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        batch.draw(img, 0f, 0f)
        batch.end()
    }
}