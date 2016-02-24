package com.jeff.game.scene;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jeff.game.Main;

import java.util.List;
import java.util.Map;

/**
 * Class to describe an abstract scene.
 */
public abstract class Scene implements Screen {

    /**
     * @return A list of the resources this scene depends upon.
     */
    public abstract Map<String,Class<? extends Disposable>> dependentResources();

    /**
     * Method that must be called to prepare the scene for being usable.
     *
     * @param application
     *         The driving class of the application.
     * @param requiredResources
     *         The resources this scene requires.
     */
    public abstract void create(Main application, Map<String, Disposable> requiredResources);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void show();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void hide();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void resize(int width, int height);

    /**
     * Method called to update the scene.
     *
     * @param delta
     *         delta time.
     */
    protected abstract void update(float delta);

    /**
     * Method called to draw the scene.
     *
     * @param delta
     *         The delta time.
     */
    protected abstract void draw(float delta);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void pause();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void resume();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void dispose();

    /**
     * {@inheritDoc} Updates then draws.
     */
    @Override
    public final void render(float delta) {
        update(delta);
        draw(delta);
    }

}
