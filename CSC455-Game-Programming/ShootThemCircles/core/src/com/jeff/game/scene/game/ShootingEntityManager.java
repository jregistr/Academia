package com.jeff.game.scene.game;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.google.common.base.Preconditions;
import com.jeff.game.managers.InputManager;
import com.jeff.game.managers.InputManager.EventType;
import com.jeff.game.models.components.*;
import com.jeff.game.models.components.RendererComponent.TextureInfo;
import com.jeff.game.models.systems.BulletSystem;
import com.jeff.game.models.util.Tag;
import com.jeff.game.util.Function;
import com.jeff.game.util.Subscriber;


/**
 * Bullet entity manager.
 */
class ShootingEntityManager implements EntityManager, Subscriber {

    private static final float CANNON_WIDTH = 95;
    private static final float CANNON_HEIGHT = 110;
    private static final float CANNON_X =
            (Gdx.graphics.getWidth() / 2.0f) - (CANNON_WIDTH / 2.0f);

    private static final float ROCKET_WIDTH = 50;
    private static final float ROCKET_HEIGHT = 100;
    private static final float ROCKET_X =
            (Gdx.graphics.getWidth() / 2.0f) - (ROCKET_WIDTH / 2.0f);
    private static final float ROCKET_Y = ROCKET_HEIGHT;
    private static final float CANNON_Y = 50;

    private final Engine engine;
    private final SpriteBatch batch;

    private final TextureRegion cannonTex;
    private final TextureRegion bulletTex;
    private final Entity cannon;
    private final BulletSystem system;
    private long nextFireTime = TimeUtils.millis();

    private final ComponentMapper<TransformComponent> t = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<VelocityComponent> v = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<RendererComponent> r = ComponentMapper.getFor(RendererComponent.class);

    private final Family bulletFamily = Family.all(TransformComponent.class,
            VelocityComponent.class, RendererComponent.class,
            CircleColliderComponent.class, BulletComponent.class).get();

    private final Family cannonFamily = Family.all(TransformComponent.class,
            RendererComponent.class, CannonComponent.class).get();

    private final Sound shootSound;

    public ShootingEntityManager(Engine engine, SpriteBatch batch,
                                 TextureRegion cannonTex, TextureRegion bulletTex, Sound shootSound) {
        this.engine = Preconditions.checkNotNull(engine);
        this.batch = Preconditions.checkNotNull(batch);
        this.cannonTex = Preconditions.checkNotNull(cannonTex);
        this.bulletTex = Preconditions.checkNotNull(bulletTex);
        this.shootSound = Preconditions.checkNotNull(shootSound);
        system = new BulletSystem();
        engine.addSystem(system);
        cannon = addCannon();
        addBullets();
        addEventListeners();
    }

    private void addEventListeners() {
        InputManager inputManager = InputManager.getInstance();
        inputManager.sub(EventType.CLICK, this, new Function<Object[], Void>() {
            @Override
            public Void apply(Object[] objects) {
                if (TimeUtils.millis() >= nextFireTime) {
                    system.shoot(ROCKET_X, ROCKET_Y);
                    nextFireTime = TimeUtils.millis() + 500;
                    shootSound.play(0.5f);
                }
                return null;
            }
        });
    }

    private Entity addCannon() {

        Array<TextureInfo> txs = new Array<TextureInfo>();
        txs.add(new TextureInfo(cannonTex, CANNON_WIDTH, CANNON_HEIGHT, 0, 0, Color.WHITE));

        Entity entity = new Entity()
                .add(new TransformComponent(CANNON_X, CANNON_Y))
                .add(new RendererComponent(new ImmutableArray<TextureInfo>(txs)))
                .add(new CannonComponent());

        engine.addEntity(entity);
        return entity;
    }

    private void addBullets() {
        for (int i = 0; i < 50; i++) {
            CircleColliderComponent c = new CircleColliderComponent(ROCKET_WIDTH / 2.0f, tags());
            c.enabled = false;
            engine.addEntity(new Entity()
                    .add(new IdentityComponent(Tag.PROJECTILE, i))
                    .add(new TransformComponent(-100, -100))
                    .add(new VelocityComponent())
                    .add(new RendererComponent(bulletInfo()))
                    .add(c)
                    .add(new BulletComponent())
                    .add(new InActiveBullet()));
        }
    }

    private ImmutableArray<TextureInfo> bulletInfo() {
        TextureInfo tex = new TextureInfo(bulletTex, ROCKET_WIDTH, ROCKET_HEIGHT, 0, 0, Color.WHITE);
        Array<TextureInfo> array = new Array<TextureInfo>();
        array.add(tex);
        return new ImmutableArray<TextureInfo>(array);
    }

    private ImmutableArray<Tag> tags() {
        Array<Tag> tagsz = new Array<Tag>();
        tagsz.add(Tag.CIRCLE);
        return new ImmutableArray<Tag>(tagsz);
    }

    @Override
    public void draw() {
        final ImmutableArray<Entity> cannons = engine.getEntitiesFor(cannonFamily);
        final ImmutableArray<Entity> bullets = engine.getEntitiesFor(bulletFamily);
        for (Entity cannon : cannons) {
            doDraw(t.get(cannon), r.get(cannon));
        }

        for (Entity bullet : bullets) {
            doDraw(t.get(bullet), r.get(bullet));
        }
    }

    private void doDraw(TransformComponent tc, RendererComponent rc) {
        if (rc.enabled) {
            Color mainColor = rc.color;
            ImmutableArray<TextureInfo> regions = rc.textureInfos;
            for (TextureInfo info : regions) {
                float w = info.width;
                float h = info.height;
                Color net = info.color.mul(mainColor);
                Color b4 = batch.getColor();
                batch.setColor(net);
                batch.draw(info.textureRegion, tc.pX + info.oX, tc.pY + info.oY,
                        w / 2, h, w, h, tc.sX, tc.sY, tc.rotation);
                batch.setColor(b4);
            }
        }
    }

}
