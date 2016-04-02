package com.jeff.chaser.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jeff.chaser.Chaser;
import com.jeff.chaser.util.Constants;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = Constants.TITLE();
        config.width = Constants.WIDTH();
        config.height = Constants.HEIGHT();
        config.resizable = false;
        new LwjglApplication(new Chaser(), config);
    }
}
