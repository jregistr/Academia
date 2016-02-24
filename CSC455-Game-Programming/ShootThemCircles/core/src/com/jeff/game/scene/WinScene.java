package com.jeff.game.scene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.google.common.collect.ImmutableMap;
import com.jeff.game.Main;

import java.util.Map;

import static com.jeff.game.Constants.FLDR_ATLASES;
import static com.jeff.game.Constants.FLDR_MUSIC;
import static com.jeff.game.Constants.uri;

public class WinScene extends Scene {
    //62 w, 18l
    private static final String WIN_ATLAS = uri(FLDR_ATLASES, "win.txt");
    private static final String CENAAA = uri(FLDR_MUSIC, "cena.mp3");

    private Animation animation;
    private Sound cena;
    private float elapsed = 0;
    SpriteBatch batch = new SpriteBatch();

    @Override
    public Map<String, Class<? extends Disposable>> dependentResources() {
        return new ImmutableMap.Builder<String, Class<? extends Disposable>>()
                .put(WIN_ATLAS, TextureAtlas.class)
                .put(CENAAA, Sound.class)
                .build();
    }

    @Override
    public void create(Main application, Map<String, Disposable> requiredResources) {
        TextureAtlas atlas = (TextureAtlas) requiredResources.get(WIN_ATLAS);
        animation = new Animation(1 / 15f, atlas.getRegions(), PlayMode.LOOP);
        cena = (Sound) requiredResources.get(CENAAA);
    }

    @Override
    public void show() {
        cena.play(1);
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
        batch.begin();
        batch.draw(animation.getKeyFrame(elapsed, true), 0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
