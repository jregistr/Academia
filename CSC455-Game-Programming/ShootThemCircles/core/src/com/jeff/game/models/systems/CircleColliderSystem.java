package com.jeff.game.models.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Circle;
import com.jeff.game.models.components.CircleColliderComponent;
import com.jeff.game.models.components.IdentityComponent;
import com.jeff.game.models.components.TransformComponent;

/**
 * Circle collider component system.
 */
public final class CircleColliderSystem extends AbstractEntitySystem {

    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<CircleColliderComponent> cm = ComponentMapper.getFor(CircleColliderComponent.class);
    private ComponentMapper<IdentityComponent> im = ComponentMapper.getFor(IdentityComponent.class);

    private final Circle protagonist = new Circle();
    private final Circle antagonist = new Circle();

    /**
     * Constructor.
     */
    public CircleColliderSystem() {
        super(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Family getFamily() {
        return Family.all(TransformComponent.class, IdentityComponent.class, CircleColliderComponent.class).get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(float deltaTime) {
        if (checkProcessing()) {
            for (Entity entity : entities) {
                CircleColliderComponent protaC = cm.get(entity);

                if (protaC.enabled) {//is it enabled
                    //get required components
                    TransformComponent protaT = tm.get(entity);
                    IdentityComponent protaI = im.get(entity);
                    protagonist.setPosition(protaT.pX + protaC.centerX, protaT.pY + protaC.centerY);
                    protagonist.setRadius(protaC.radius);

                    int entCount = entities.size();
                    for (int i = 0; i < entCount; i++) {
                        Entity possibleAnt = entities.get(i);
                        TransformComponent antaT = tm.get(possibleAnt);
                        CircleColliderComponent antaC = cm.get(possibleAnt);
                        // IdentityComponent antaI = im.get(possibleAnt);
                        //antaC.enabled && antaC.collideAbleTags.contains(protaI.tag, false)

                        // System.out.println("PROTAC:" + );

                        if (antaC.enabled && antaC.collideAbleTags.contains(protaI.tag, false)) {//Can you even collide? Can you collide with that fella?
                            //   System.out.println("Possible");
                            antagonist.setPosition(antaT.pX + antaC.centerX, antaT.pY + antaC.centerY);
                            antagonist.setRadius(antaC.radius);
                            if (protagonist.overlaps(antagonist)) {//We have collision
                                protaC.collided = true;
                                antaC.collided = true;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void removedFromEngineAdditional(Engine engine) {
        tm = null;
        cm = null;
    }
}
