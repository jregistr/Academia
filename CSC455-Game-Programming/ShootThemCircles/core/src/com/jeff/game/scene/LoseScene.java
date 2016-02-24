package com.jeff.game.scene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.google.common.collect.ImmutableMap;
import com.jeff.game.Main;

import java.util.Map;

import static com.jeff.game.Constants.*;

public class LoseScene extends Scene {

    private static final String WIN_ATLAS = uri(FLDR_ATLASES, "loss.txt");
    private static final String PEPE = uri(FLDR_IMAGES, FLDR_MISC, "pepe.png");
    private static final String HELLO_DARKNESS = uri(FLDR_MUSIC, "darkness.mp3");

    private Animation animation;
    private Texture pepe;
    private Sound darkness;

    private float elapsed = 0;
    SpriteBatch batch = new SpriteBatch();
    private final float totalFade = 10;

    private final int w = Gdx.graphics.getWidth();
    private final int h = Gdx.graphics.getHeight();

    @Override
    public Map<String, Class<? extends Disposable>> dependentResources() {
        return new ImmutableMap.Builder<String, Class<? extends Disposable>>()
                .put(WIN_ATLAS, TextureAtlas.class)
                .put(PEPE, Texture.class)
                .put(HELLO_DARKNESS, Sound.class)
                .build();
    }

    @Override
    public void create(Main application, Map<String, Disposable> requiredResources) {
        TextureAtlas atlas = (TextureAtlas) requiredResources.get(WIN_ATLAS);
        animation = new Animation(1 / 10f, atlas.getRegions(), PlayMode.LOOP);
        pepe = (Texture) requiredResources.get(PEPE);
        darkness = (Sound) requiredResources.get(HELLO_DARKNESS);
    }

    @Override
    public void show() {
        darkness.play(1);
    }

    @Override
    public void hide() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    protected void update(float delta) {
        elapsed += Gdx.graphics.getDeltaTime();
    }

    @Override
    protected void draw(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Color b4 = batch.getColor();
        batch.begin();
        batch.setColor(new Color(elapsed/totalFade,elapsed/totalFade,elapsed/totalFade, elapsed/totalFade));
        batch.draw(pepe,0,0,w,h);
        batch.setColor(b4);
        batch.draw(animation.getKeyFrame(elapsed, true), 0, 0,w/2.0f, h/2.0f);
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
