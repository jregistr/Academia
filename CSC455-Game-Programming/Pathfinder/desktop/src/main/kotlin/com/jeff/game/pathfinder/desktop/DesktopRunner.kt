package com.jeff.game.pathfinder.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.jeff.game.pathfinder.PathFinder

fun main(args: Array<String>) {
    val config = LwjglApplicationConfiguration()
    config.apply {
        title = "PathFinder"
        width = 960
        height = 540
        resizable = false
    }
    LwjglApplication(PathFinder(), config)
}