package com.jeff.game.models.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.jeff.game.models.components.TransformComponent;
import com.jeff.game.models.components.VelocityComponent;

/**
 * Velocity system.
 */
public class VelocitySystem extends IteratingSystem {

    private final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public VelocitySystem() {
        super(Family.all(TransformComponent.class, VelocityComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final TransformComponent t = tm.get(entity);
        final VelocityComponent v = vm.get(entity);
        t.pX += (v.x * deltaTime);
        t.pY += (v.y * deltaTime);
    }
}
