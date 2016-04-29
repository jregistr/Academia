package com.jeff.game.castlesmack.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.MathUtils;
import com.jeff.game.castlesmack.CastleSmack;
import com.jeff.game.castlesmack.util.constant.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = MathUtils.round(Constants.meterToPix(100f));
        config.height = MathUtils.round(Constants.meterToPix(52));
        config.title = "Castle Smack";
        new LwjglApplication(new CastleSmack(), config);
	}
}
