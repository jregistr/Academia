package com.jeff.game.scene.game;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.jeff.game.models.components.*;
import com.jeff.game.models.components.RendererComponent.TextureInfo;
import com.jeff.game.models.systems.BubbleSystem;
import com.jeff.game.models.systems.CircleColliderSystem;
import com.jeff.game.models.systems.VelocitySystem;
import com.jeff.game.models.util.Tag;
import com.jeff.game.util.Function;
import com.jeff.game.util.Pair;

import java.util.Map;

import static com.jeff.game.scene.game.GameSceneConstants.RingConstants.*;

/**
 * Class to kick of the main game logic.
 */
class RingEntityManager implements EntityManager {

    private static final float SHOW_CONFIG_TOP_X = Gdx.graphics.getWidth() * 0.85f;
    private static final float SHOW_CONFIG_TOP_y = Gdx.graphics.getHeight() / 4f;
    private static final float SHOW_CONFIG_RADIUS = 50;
    private static final float SHOW_CONFIG_PADDING = 10;

    private Engine engine;
    private final SpriteBatch batch;
    private final BubbleSystem bubbleSystem;
    private final TextureRegion circle;

    private ComponentMapper<RendererComponent> r = ComponentMapper.getFor(RendererComponent.class);
    private ComponentMapper<TransformComponent> t = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<CircleColliderComponent> c = ComponentMapper.getFor(CircleColliderComponent.class);

    private Family renderFamily = Family.all(TransformComponent.class, RendererComponent.class, BubbleComponent.class).get();

    public RingEntityManager(Engine engine, SpriteBatch batch, Map<String, TextureRegion> ringItems, TextureRegion circle, TextureRegion circleRim, Function<Boolean, Void> onBubblePop) {
        this.engine = Preconditions.checkNotNull(engine);
        Preconditions.checkNotNull(ringItems);
        this.batch = Preconditions.checkNotNull(batch);
        this.circle = Preconditions.checkNotNull(circle);

        engine.addSystem(new CircleColliderSystem());
        engine.addSystem(new VelocitySystem());
        bubbleSystem = new BubbleSystem(
                ImmutableList.<TextureRegion>of(
                        ringItems.get(GameSceneConstants.BEAR),
                        ringItems.get(GameSceneConstants.FEELS),
                        ringItems.get(GameSceneConstants.FOREVER),
                        ringItems.get(GameSceneConstants.HEAVY),
                        ringItems.get(GameSceneConstants.KERMIT),
                        ringItems.get(GameSceneConstants.PEPE),
                        ringItems.get(GameSceneConstants.TROLL),
                        ringItems.get(GameSceneConstants.OBJ_1),
                        ringItems.get(GameSceneConstants.OBJ_2),
                        ringItems.get(GameSceneConstants.OBJ_3)
                ),
                circle, Preconditions.checkNotNull(circleRim), onBubblePop
        );
        engine.addSystem(bubbleSystem);

        //Add entities
        int index = addOuters(0);
        index = addMiddle(index);
        addInner(index);
    }

    public void preProcess() {
        bubbleSystem.preUpdate();
    }

    @Override
    public void draw() {
        final ImmutableArray<Entity> renderables = engine.getEntitiesFor(renderFamily);
        Color b4 = batch.getColor();
        for (Entity entity : renderables) {
            RendererComponent rc = r.get(entity);
            TransformComponent tc = t.get(entity);
            if (rc.enabled) {
                Color mainColor = rc.color;
                ImmutableArray<TextureInfo> regions = rc.textureInfos;
                for (TextureInfo info : regions) {
                    float w = info.width;
                    float h = info.height;
                    Color net = info.color.mul(mainColor);

                    batch.setColor(net);
                    batch.draw(info.textureRegion, tc.pX + info.oX, tc.pY + info.oY,
                            w / 2, h / 2, w, h, tc.sX, tc.sY, tc.rotation);

                }
            }
        }

        final ImmutableArray<Pair<Color, TextureRegion>> allows = bubbleSystem.getAllowedTex();
        int count = 0;
        for (Pair<Color, TextureRegion> regionPair : allows) {
            batch.setColor(regionPair.key);
            batch.draw(circle, SHOW_CONFIG_TOP_X, SHOW_CONFIG_TOP_y - (SHOW_CONFIG_RADIUS * count),
                    SHOW_CONFIG_RADIUS * 1.05f, SHOW_CONFIG_RADIUS * 1.05f);
            batch.setColor(Color.WHITE);
            batch.draw(regionPair.value, SHOW_CONFIG_TOP_X, SHOW_CONFIG_TOP_y - (SHOW_CONFIG_RADIUS * count),
                    SHOW_CONFIG_RADIUS * 0.85f, SHOW_CONFIG_RADIUS * 0.85f);
            count++;
        }

        batch.setColor(b4);
    }

    private int addOuters(int start) {
        //(x,y) = (cx + r * cos(theta), cy + r * sin(theta))

        final float cX = (getCenterX() - (OUTER_WIDTH / 2));
        final float cY = (getCenterY() - (OUTER_HEIGHT / 2));

        float x1 = cX + (RADIUS_OUTER * MathUtils.cosDeg(90));
        float y1 = cY + (RADIUS_OUTER * MathUtils.sinDeg(90));

        float x2 = cX + (RADIUS_OUTER * MathUtils.cosDeg(195));
        float y2 = cY + (RADIUS_OUTER * MathUtils.sinDeg(195));

        float x3 = cX + (RADIUS_OUTER * MathUtils.cosDeg(345));
        float y3 = cY + (RADIUS_OUTER * MathUtils.sinDeg(345));

        addBubbleEntity(start, x1, y1, RADIUS_OUTER, OUTER_WIDTH, OUTER_HEIGHT, 90);
        start++;
        addBubbleEntity(start, x2, y2, RADIUS_OUTER, OUTER_WIDTH, OUTER_HEIGHT, 195);
        start++;
        addBubbleEntity(start, x3, y3, RADIUS_OUTER, OUTER_WIDTH, OUTER_HEIGHT, 345);
        start++;

        return start;
    }

    private int addMiddle(int start) {
        final float cX = (getCenterX() - (MIDDLE_WIDTH / 2));
        final float cY = (getCenterY() - (MIDDLE_HEIGHT / 2));

        float x1 = cX + (RADIUS_MIDDLE * MathUtils.cosDeg(35));
        float y1 = cY + (RADIUS_MIDDLE * MathUtils.sinDeg(35));

        float x2 = cX + (RADIUS_MIDDLE * MathUtils.cosDeg(145));
        float y2 = cY + (RADIUS_MIDDLE * MathUtils.sinDeg(145));

        float x3 = cX + (RADIUS_MIDDLE * MathUtils.cosDeg(270));
        float y3 = cY + (RADIUS_MIDDLE * MathUtils.sinDeg(270));

        addBubbleEntity(start, x1, y1, RADIUS_MIDDLE, MIDDLE_WIDTH, MIDDLE_HEIGHT, 35);
        start++;
        addBubbleEntity(start, x2, y2, RADIUS_MIDDLE, MIDDLE_WIDTH, MIDDLE_HEIGHT, 145);
        start++;
        addBubbleEntity(start, x3, y3, RADIUS_MIDDLE, MIDDLE_WIDTH, MIDDLE_HEIGHT, 270);
        start++;
        return start;
    }

    private int addInner(int start) {
        final float cX = (getCenterX() - (INNER_WIDTH / 2));
        final float cY = (getCenterY() - (INNER_HEIGHT / 2));

        float x1 = cX + (RADIUS_INNER * MathUtils.cosDeg(0));
        float y1 = cY + (RADIUS_INNER * MathUtils.sinDeg(0));

        float x2 = cX + (RADIUS_INNER * MathUtils.cosDeg(45));
        float y2 = cY + (RADIUS_INNER * MathUtils.sinDeg(45));

        float x3 = cX + (RADIUS_INNER * MathUtils.cosDeg(135));
        float y3 = cY + (RADIUS_INNER * MathUtils.sinDeg(135));

        float x4 = cX + (RADIUS_INNER * MathUtils.cosDeg(180));
        float y4 = cY + (RADIUS_INNER * MathUtils.sinDeg(180));

        float x5 = cX + (RADIUS_INNER * MathUtils.cosDeg(225));
        float y5 = cY + (RADIUS_INNER * MathUtils.sinDeg(225));

        float x6 = cX + (RADIUS_INNER * MathUtils.cosDeg(315));
        float y6 = cY + (RADIUS_INNER * MathUtils.sinDeg(315));

        addBubbleEntity(start, x1, y1, RADIUS_INNER, INNER_WIDTH, INNER_HEIGHT, 0);
        start++;
        addBubbleEntity(start, x2, y2, RADIUS_INNER, INNER_WIDTH, INNER_HEIGHT, 45);
        start++;
        addBubbleEntity(start, x3, y3, RADIUS_INNER, INNER_WIDTH, INNER_HEIGHT, 135);
        start++;
        addBubbleEntity(start, x4, y4, RADIUS_INNER, INNER_WIDTH, INNER_HEIGHT, 180);
        start++;
        addBubbleEntity(start, x5, y5, RADIUS_INNER, INNER_WIDTH, INNER_HEIGHT, 225);
        start++;
        addBubbleEntity(start, x6, y6, RADIUS_INNER, INNER_WIDTH, INNER_HEIGHT, 315);
        start++;

        return start;
    }

    private void addBubbleEntity(int id, float x, float y, float r, float w, float h, float rot) {
        engine.addEntity(new Entity()
                .add(new IdentityComponent(Tag.CIRCLE, id))
                .add(new TransformComponent(x, y))
                .add(new CircleColliderComponent(w / 2.0f, circleCollideAbleTags()))
                .add(new RendererComponent(new ImmutableArray<TextureInfo>(new Array<TextureInfo>())))
                .add(new BubbleComponent(r, w, h, rot, 0, 0)));
    }

    private ImmutableArray<Tag> circleCollideAbleTags() {
        Array<Tag> tags = new Array<Tag>();
        tags.add(Tag.PROJECTILE);
        return new ImmutableArray<Tag>(tags);
    }


}
