package com.jeff.game.scene.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.jeff.game.Main;
import com.jeff.game.scene.Scene;
import com.jeff.game.util.Function;

import java.util.Map;

import static com.jeff.game.scene.game.GameSceneConstants.*;

/**
 * The scene holding the game play logic.
 */
public class GameScene extends Scene {

    private Main app;
    private RingEntityManager ringEntityManager;
    private ShootingEntityManager shootingEntityManager;
    private SpriteBatch batch = new SpriteBatch();

    private Texture sky, ground, cloud1, cloud2, cloud3, cloud4;
    private Engine engine;
    private float w, h;
    private float w2, h2;

    private static final Color skyColor = new Color(107.0f / 255.0f,
            227.0f / 255.0f, 227.0f / 255.0f, 1.0f);

    private int score = 0;
    private final BitmapFont font = new BitmapFont();

    private static final int ADD_GOOD = 100;
    private static final int ADD_BAD = -50;
    private static final int LOSS_GAME = -200;
    private static final int WIN_GAME = 400;

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Class<? extends Disposable>> dependentResources() {
        return new ImmutableMap.Builder<String, Class<? extends Disposable>>()
                .put(CIRCLE_FILL, Texture.class)
                .put(CIRCLE_FILL_FADED, Texture.class)
                .put(CIRCLE_OUTLINE, Texture.class)
                .put(LINE, Texture.class)
                .put(BEAR, Texture.class)
                .put(FEELS, Texture.class)
                .put(FOREVER, Texture.class)
                .put(HEAVY, Texture.class)
                .put(KERMIT, Texture.class)
                .put(PEPE, Texture.class)
                .put(OBJ_1, Texture.class)
                .put(OBJ_2, Texture.class)
                .put(OBJ_3, Texture.class)
                .put(TROLL, Texture.class)
                .put(CANNON, Texture.class)
                .put(ROCKET, Texture.class)
                .put(SKY, Texture.class)
                .put(GROUND, Texture.class)
                .put(CLOUD1, Texture.class)
                .put(CLOUD2, Texture.class)
                .put(CLOUD3, Texture.class)
                .put(CLOUD4, Texture.class)
                .put(SHOOT, Sound.class)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Main application, Map<String, Disposable> requiredResources) {
        app = application;
        engine = new Engine();
        w = Gdx.graphics.getWidth();
        w2 = w / 2.0f;
        h = Gdx.graphics.getHeight();
        h2 = h / 2.0f;
        ringEntityManager = new RingEntityManager(engine, batch, new ImmutableMap.Builder<String, TextureRegion>()
                .put(BEAR, new TextureRegion(Preconditions.checkNotNull((Texture) requiredResources.get(BEAR))))
                .put(FEELS, new TextureRegion(Preconditions.checkNotNull((Texture) requiredResources.get(FEELS))))
                .put(FOREVER, new TextureRegion(Preconditions.checkNotNull((Texture) requiredResources.get(FOREVER))))
                .put(HEAVY, new TextureRegion(Preconditions.checkNotNull((Texture) requiredResources.get(HEAVY))))
                .put(KERMIT, new TextureRegion(Preconditions.checkNotNull((Texture) requiredResources.get(KERMIT))))
                .put(PEPE, new TextureRegion(Preconditions.checkNotNull((Texture) requiredResources.get(PEPE))))
                .put(OBJ_1, new TextureRegion(Preconditions.checkNotNull((Texture) requiredResources.get(OBJ_1))))
                .put(OBJ_2, new TextureRegion(Preconditions.checkNotNull((Texture) requiredResources.get(OBJ_2))))
                .put(OBJ_3, new TextureRegion(Preconditions.checkNotNull((Texture) requiredResources.get(OBJ_3))))
                .put(TROLL, new TextureRegion(Preconditions.checkNotNull((Texture) requiredResources.get(TROLL))))
                .build(), new TextureRegion(Preconditions.checkNotNull((Texture) requiredResources.get(CIRCLE_FILL))),
                new TextureRegion(Preconditions.checkNotNull((Texture) requiredResources.get(CIRCLE_OUTLINE))),
                new Function<Boolean, Void>() {
                    @Override
                    public Void apply(Boolean aBoolean) {
                        bubblePop(aBoolean);
                        return null;
                    }
                });
        //obj = new TextureRegion(Preconditions.checkNotNull((Texture) requiredResources.get(CIRCLE_FILL)));
        sky = (Texture) requiredResources.get(SKY);
        ground = (Texture) requiredResources.get(GROUND);
        cloud1 = (Texture) requiredResources.get(CLOUD1);
        cloud2 = (Texture) requiredResources.get(CLOUD2);
        cloud3 = (Texture) requiredResources.get(CLOUD3);
        cloud4 = (Texture) requiredResources.get(CLOUD4);

        shootingEntityManager = new ShootingEntityManager(engine, batch,
                new TextureRegion(Preconditions.checkNotNull((Texture) requiredResources.get(CANNON))),
                new TextureRegion(Preconditions.checkNotNull((Texture) requiredResources.get(ROCKET))),
                (Sound) requiredResources.get(SHOOT));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void update(float delta) {
        ringEntityManager.preProcess();
        engine.update(delta);
    }

    private void bubblePop(boolean good) {
        score += good ? ADD_GOOD : ADD_BAD;
        if(score <= LOSS_GAME){
            app.goToLoss();
        }else if(score >= WIN_GAME){
            app.goToWin();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void draw(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Color b4 = batch.getColor();
        batch.begin();
        drawBackGround();
        ringEntityManager.draw();
        shootingEntityManager.draw();
        batch.setColor(b4);
        batch.end();

        batch.begin();

        font.draw(batch, "Score:" + score, w * 0.80f, h);

        batch.end();

    }

    private void drawBackGround() {

        batch.setColor(skyColor);
        batch.draw(sky,0,0,w,h);
        batch.setColor(Color.WHITE);
        batch.draw(ground, 0, 0, w, 110);
        batch.setColor(1,1,1, 0.8f);
        batch.draw(cloud1, 0, h - 150, w * 0.75f, 200);
        batch.setColor(1,1,1, 0.5f);
        batch.draw(cloud2, 10, 400, 90, 40);
        batch.draw(cloud3, 0, 600, 120, 60);
        batch.draw(cloud4, w * 0.85f, 400, 120, 70);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {

    }
}
