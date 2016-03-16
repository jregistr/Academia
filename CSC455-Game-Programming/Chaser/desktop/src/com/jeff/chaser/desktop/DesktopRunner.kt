package com.jeff.chaser.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.jeff.chaser.Chaser

fun main(args:Array<String>){
    val config = LwjglApplicationConfiguration()
    config.apply {
        title = "Don't go near the guard"
        width = 1500
        height = 640
        resizable = false
    }
    LwjglApplication(Chaser(), config)
}
