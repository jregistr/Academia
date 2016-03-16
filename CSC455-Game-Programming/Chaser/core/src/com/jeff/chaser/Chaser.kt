package com.jeff.chaser

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch


class Chaser : ApplicationAdapter() {

    private var batch:SpriteBatch? = null
    private var tex:Texture? = null

    override fun create() {
        batch = SpriteBatch()
        tex = Texture("badlogic.jpg")
    }

    override fun render() {
        val batch = this.batch!!
        val tex = this.tex!!
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        batch.draw(tex, 0f, 0f)
        batch.end()
    }

}