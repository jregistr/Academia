package com.jeff.game.castlesmack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.jeff.game.castlesmack.system.GameManager;
import com.jeff.game.castlesmack.util.constant.Constants;
import com.jeff.game.castlesmack.util.constant.Constants.TexConstants;


public class CastleSmack extends ApplicationAdapter {

    private World world;
    private Box2DDebugRenderer renderer;
    private Matrix4 debugMatrix;
    private AssetManager manager;
    private GameManager gameManager;

    @Override
    public void create() {
        manager = new AssetManager();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        world = new World(new Vector2(0, 9.81f), true);
        debugMatrix = new Matrix4(camera.combined);
        debugMatrix.scale(Constants.M_TO_PIX, Constants.M_TO_PIX, 1f);
        renderer = new Box2DDebugRenderer();

        manager.load(TexConstants.HOUSE, Texture.class);
        manager.load(TexConstants.ISLAND, Texture.class);
        manager.load(TexConstants.PIPE, Texture.class);
        manager.load(TexConstants.ROCK, Texture.class);

        manager.finishLoading();

        ObjectMap<String, Texture> map = new ObjectMap<String, Texture>();
        map.put(TexConstants.HOUSE, manager.get(TexConstants.HOUSE, Texture.class));
        map.put(TexConstants.ISLAND, manager.get(TexConstants.ISLAND, Texture.class));
        map.put(TexConstants.PIPE, manager.get(TexConstants.PIPE, Texture.class));
        map.put(TexConstants.ROCK, manager.get(TexConstants.ROCK, Texture.class));

        gameManager = new GameManager(world, false, map);

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1 / 60f, 10, 8);
        gameManager.runCollisions();
        gameManager.update(Gdx.graphics.getDeltaTime());
        renderer.render(world, debugMatrix);
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        renderer.dispose();
        world.dispose();
    }
}
