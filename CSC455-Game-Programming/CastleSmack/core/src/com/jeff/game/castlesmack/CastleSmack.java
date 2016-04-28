package com.jeff.game.castlesmack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.jeff.game.castlesmack.util.constant.Constants;

public class CastleSmack extends ApplicationAdapter {

    private World world;
    private Box2DDebugRenderer renderer;
    private Matrix4 debugMatrix;

    @Override
    public void create() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        world = new World(new Vector2(0, 9.18f), true);
        debugMatrix = new Matrix4(camera.combined);
        debugMatrix.scale(Constants.M_TO_PIX, Constants.M_TO_PIX, 1f);
        renderer = new Box2DDebugRenderer();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1 / 60f, 10, 8);
        renderer.render(world, debugMatrix);
    }
}
