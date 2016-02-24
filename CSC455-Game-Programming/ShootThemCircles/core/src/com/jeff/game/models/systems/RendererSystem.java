package com.jeff.game.models.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.common.base.Preconditions;
import com.jeff.game.models.components.RendererComponent;
import com.jeff.game.models.components.TransformComponent;

/**
 * RendererComponent component system.
 */
public final class RendererSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<RendererComponent> rm = ComponentMapper.getFor(RendererComponent.class);

    private SpriteBatch batch;

    public RendererSystem(SpriteBatch batch) {
        super(Family.all(TransformComponent.class, RendererComponent.class).get(), 100);
        this.batch = Preconditions.checkNotNull(batch);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        /*RendererComponent r = rm.get(entity);
        TransformComponent t = tm.get(entity);
        final float w = r.width;
        final float h = r.height;
        Color prev = batch.getColor();
        batch.setColor(r.color);
        batch.draw(r.texture, t.pX, t.pY, w / 2, h / 2, w, h, t.sX, t.sY, t.rotation);
        batch.setColor(prev);*/
    }

}
