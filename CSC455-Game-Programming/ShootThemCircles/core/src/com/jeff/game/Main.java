package com.jeff.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.jeff.game.managers.InputManager;
import com.jeff.game.scene.LoseScene;
import com.jeff.game.scene.Scene;
import com.jeff.game.scene.WinScene;
import com.jeff.game.scene.game.GameScene;

import java.util.Map;
import java.util.Set;

/**
 * Main core entry point to the game.
 */
public class Main extends Game {

    private AssetManager assetManager;
   // private GameScene gameScene;
    private FitViewport viewport;

    /**
     * {@inheritDoc} Sets up the environment to ge the game running.
     */
    @Override
    public void create() {
        Gdx.input.setInputProcessor(InputManager.getInstance());
        assetManager = new AssetManager();
       // gameScene = new GameScene();
        int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        Camera camera = new OrthographicCamera(w, h);
        viewport = new FitViewport(w, h, camera);

        setCurrentScene(new GameScene());
    }

    private void setCurrentScene(Scene scene){
        Map<String, Class<? extends Disposable>> dependentResources = scene.dependentResources();
        Set<String> names = dependentResources.keySet();
        for (String name : names) {
            assetManager.load(name, dependentResources.get(name));
        }

        assetManager.finishLoading();

        ImmutableMap.Builder<String, Disposable> disposableBuilder = new ImmutableBiMap.Builder<String, Disposable>();
        for (String name : names) {
            disposableBuilder.put(name, Preconditions.checkNotNull(
                    assetManager.get(name,
                    dependentResources.get(name))));
        }

        if(getScreen() != null){
            Screen screen = getScreen();
            setScreen(null);
            screen.hide();
            scene.dispose();
        }

        scene.create(this, disposableBuilder.build());
        setScreen(scene);
    }

    public void goToLoss(){
        setCurrentScene(new LoseScene());
    }

    public void goToWin(){
        setCurrentScene(new WinScene());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        if(getScreen() != null){
            Screen screen = getScreen();
            setScreen(null);
            screen.hide();
            screen.dispose();
        }
        assetManager.dispose();
    }

    public FitViewport getViewport() {
        return viewport;
    }
}
