package com.jeff.game.models.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.jeff.game.models.components.*;
import com.jeff.game.util.Function;

/**
 * Bullet system
 */
public class BulletSystem extends EntitySystem {

    private ImmutableArray<Entity> activeBullets;
    private ImmutableArray<Entity> inActiveBullets;

    private final ComponentMapper<TransformComponent> t = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<VelocityComponent> v = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<CircleColliderComponent> c = ComponentMapper.getFor(CircleColliderComponent.class);
    private final ComponentMapper<RendererComponent> r = ComponentMapper.getFor(RendererComponent.class);

    public BulletSystem() {

    }

    @Override
    public void addedToEngine(final Engine engine) {
        final Family active = Family.all(TransformComponent.class,
                VelocityComponent.class, RendererComponent.class,
                CircleColliderComponent.class, BulletComponent.class).get();
        final Family inActive = Family.all(TransformComponent.class,
                VelocityComponent.class, RendererComponent.class,
                CircleColliderComponent.class, BulletComponent.class,
                InActiveBullet.class).get();
        final Function<Engine, Void> f = new Function<Engine, Void>() {
            @Override
            public Void apply(Engine input) {
                activeBullets = engine.getEntitiesFor(active);
                inActiveBullets = engine.getEntitiesFor(inActive);
                return null;
            }
        };
        f.apply(engine);

        engine.addEntityListener(active, new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                f.apply(getEngine());
            }

            @Override
            public void entityRemoved(Entity entity) {
                f.apply(getEngine());
            }
        });
    }

    @Override
    public void removedFromEngine(Engine engine) {
        activeBullets = null;
        inActiveBullets = null;
    }

    public void shoot(float x, float y){
        Entity bullet = inActiveBullets.first();
        TransformComponent tc = t.get(bullet);
        CircleColliderComponent cc = c.get(bullet);
        VelocityComponent vc = v.get(bullet);
        RendererComponent rc = r.get(bullet);
        tc.pX = x;
        tc.pY = y;
        cc.enabled = true;
        cc.collided = false;
        vc.x = 0;
        vc.y = 400;
        rc.enabled = true;
        bullet.remove(InActiveBullet.class);
    }

    @Override
    public void update(float deltaTime) {
        for (Entity activeBullet : activeBullets) {
            TransformComponent tc = t.get(activeBullet);
            CircleColliderComponent cc = c.get(activeBullet);
            VelocityComponent vc = v.get(activeBullet);
            RendererComponent rc = r.get(activeBullet);
            if (tc.pY > (Gdx.graphics.getHeight() * 1.5f) || cc.collided) {
                vc.x = 0;
                vc.y = 0;
                cc.collided = false;
                tc.pX = -100;
                tc.pY = -100;
                rc.enabled = false;
                activeBullet.add(new InActiveBullet());
            }
        }
    }
}
