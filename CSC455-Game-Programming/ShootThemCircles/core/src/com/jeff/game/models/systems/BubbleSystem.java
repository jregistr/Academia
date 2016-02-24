package com.jeff.game.models.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.jeff.game.models.components.*;
import com.jeff.game.models.components.RendererComponent.TextureInfo;
import com.jeff.game.util.Function;
import com.jeff.game.util.Pair;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static com.jeff.game.scene.game.GameSceneConstants.RingConstants.getCenterX;
import static com.jeff.game.scene.game.GameSceneConstants.RingConstants.getCenterY;

/**
 * Bubble system
 */
public class BubbleSystem extends IteratingSystem {

    private static final int MIN_REGIONS = 1;
    private static final long NUM_NO_RENABLE = -1L;
    private static final long NUM_INIT = 0L;
    private static final int RE_ENABLE_TIME = 10;
    private static final float TOF = 3.0f / 4.0f;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private final ComponentMapper<IdentityComponent> im = ComponentMapper.getFor(IdentityComponent.class);
    private final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<CircleColliderComponent> cm = ComponentMapper.getFor(CircleColliderComponent.class);
    private final ComponentMapper<RendererComponent> rm = ComponentMapper.getFor(RendererComponent.class);
    private final ComponentMapper<BubbleComponent> bm = ComponentMapper.getFor(BubbleComponent.class);

    private final Map<Integer, Color> priTypeConfig;
    private final Map<Integer, TextureRegion> secTypeConfig;
    private final TextureRegion circle, circleRim;

    private Map<Integer, Long> renableTimers = new HashMap<Integer, Long>();
    public final ImmutableArray<Pair<Integer, Integer>> allowedConfig;
    private final ImmutableArray<Pair<Color, TextureRegion>> allowedTex;

    private float rotationSpeed = 30f;
    private long nextSwitchAllowedTime = TimeUtils.millis();
    private final Function<Boolean, Void> onBubblePop;

    public BubbleSystem(ImmutableList<TextureRegion> regions, TextureRegion circle, TextureRegion circleRim, Function<Boolean, Void> onBubblePop) {
        super(Family.all(IdentityComponent.class, TransformComponent.class,
                CircleColliderComponent.class, RendererComponent.class, BubbleComponent.class).get());
        Preconditions.checkNotNull(regions);
        Preconditions.checkArgument(regions.size() > MIN_REGIONS);
        this.onBubblePop = Preconditions.checkNotNull(onBubblePop);
        this.circle = Preconditions.checkNotNull(circle);
        this.circleRim = Preconditions.checkNotNull(circleRim);

        priTypeConfig = new ImmutableMap.Builder<Integer, Color>()
                .put(0, Color.WHITE)
                .put(1, Color.CORAL)
                .put(2, Color.CYAN)
                .put(3, Color.VIOLET)
                .put(4, Color.CHARTREUSE)
                .put(5, Color.GOLDENROD)
                .put(6, Color.SALMON)
                .put(7, Color.TEAL)
                .put(8, Color.LIGHT_GRAY)
                .put(9, Color.TAN)
                .build();

        final ImmutableMap.Builder<Integer, TextureRegion> builder = new ImmutableMap.Builder<Integer, TextureRegion>();

        int index = 0;
        for (TextureRegion region : regions) {
            builder.put(index, region);
            index++;
        }

        secTypeConfig = builder.build();

        Array<Pair<Integer, Integer>> array = new Array<Pair<Integer, Integer>>();
        array.add(new Pair<Integer, Integer>(0, 0));
        array.add(new Pair<Integer, Integer>(0, 0));
        array.add(new Pair<Integer, Integer>(0, 0));

        Array<Pair<Color, TextureRegion>> array2 = new Array<Pair<Color, TextureRegion>>();
        array2.add(new Pair<Color, TextureRegion>(null, null));
        array2.add(new Pair<Color, TextureRegion>(null, null));
        array2.add(new Pair<Color, TextureRegion>(null, null));

        allowedConfig = new ImmutableArray<Pair<Integer, Integer>>(array);
        allowedTex = new ImmutableArray<Pair<Color, TextureRegion>>(array2);
        setAllowedConfig(array.get(0), array.get(1), array.get(2));
    }

    public void preUpdate() {
        processSwitchAlloweds();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final IdentityComponent id = im.get(entity);
        final TransformComponent t = tm.get(entity);
        final CircleColliderComponent c = cm.get(entity);
        final RendererComponent r = rm.get(entity);
        final BubbleComponent b = bm.get(entity);

        processEnable(id, r, b, c);
        rotate(b, t, deltaTime);
        processCollision(id, c, r, b);

    }

    private void processEnable(IdentityComponent id, RendererComponent r, BubbleComponent b, CircleColliderComponent c) {
        int entID = id.id;
        if (!renableTimers.containsKey(entID)) {
            renableTimers.put(entID, NUM_INIT);
        }

        long timer = renableTimers.get(entID);
        if (timer != NUM_NO_RENABLE && TimeUtils.millis() >= timer) {//Renable it

            int priType;
            int secType;

            int metCount = 0;
            ImmutableArray<Entity> allBubbles = getEngine()
                    .getEntitiesFor(Family.all(BubbleComponent.class).get());

            for (Entity allBubble : allBubbles) {
                BubbleComponent allBubbleBuble = bm.get(allBubble);
                if(hasConf(allBubbleBuble.firstType, allBubbleBuble.secondType)){
                    metCount++;
                }
            }

            if(metCount > 2){
                int rand = random.nextInt(101);
                if(rand <= 10){
                    int select = random.nextInt(3);
                    Pair<Integer, Integer> conf = allowedConfig.get(select);
                    priType = conf.key;
                    secType = conf.value;
                }else {
                    priType = random.nextInt(0, priTypeConfig.size());
                    secType = random.nextInt(0, secTypeConfig.size());
                }
            }else {
                int rand = random.nextInt(101);
                if(rand <= 50){
                    int select = random.nextInt(3);
                    Pair<Integer, Integer> conf = allowedConfig.get(select);
                    priType = conf.key;
                    secType = conf.value;
                }else {
                    priType = random.nextInt(0, priTypeConfig.size());
                    secType = random.nextInt(0, secTypeConfig.size());
                }
            }

            b.firstType = priType;
            b.secondType = secType;
            processConfigType(b, r);
            renableTimers.put(entID, NUM_NO_RENABLE);
            r.enabled = true;
            c.enabled = true;
        }

    }

    private boolean hasConf(int pri, int sec) {
        boolean has = false;
        for (Pair<Integer, Integer> integerIntegerPair : allowedConfig) {
            if (integerIntegerPair.key == pri && integerIntegerPair.value == sec) {
                has = true;
                break;
            }
        }
        return has;
    }

    private void processCollision(IdentityComponent i, CircleColliderComponent c, RendererComponent r, BubbleComponent b) {
        if (c.collided) {
            c.collided = false;
            r.enabled = false;
            c.enabled = false;
            renableTimers.put(i.id, TimeUtils.millis() + 2000);
            boolean good = false;
            for (Pair<Integer, Integer> integerIntegerPair : allowedConfig) {
                if (integerIntegerPair.key == b.firstType &&
                        integerIntegerPair.value == b.secondType) {
                    good = true;
                    break;
                }
            }
            onBubblePop.apply(good);
        }
    }

    private void setAllowedConfig(Pair<Integer, Integer> f, Pair<Integer, Integer> s,
                                  Pair<Integer, Integer> t) {
        allowedConfig.get(0).key = f.key;
        allowedConfig.get(0).value = f.value;
        allowedConfig.get(1).key = s.key;
        allowedConfig.get(1).value = s.value;
        allowedConfig.get(2).key = t.key;
        allowedConfig.get(2).value = t.value;

        allowedTex.get(0).key = priTypeConfig.get(f.key);
        allowedTex.get(0).value = secTypeConfig.get(f.value);

        allowedTex.get(1).key = priTypeConfig.get(s.key);
        allowedTex.get(1).value = secTypeConfig.get(s.value);

        allowedTex.get(2).key = priTypeConfig.get(t.key);
        allowedTex.get(2).value = secTypeConfig.get(t.value);
    }

    private void processSwitchAlloweds() {
        if (TimeUtils.millis() >= nextSwitchAllowedTime) {
            int p1 = random.nextInt(0, priTypeConfig.size());
            int s1 = random.nextInt(0, secTypeConfig.size());
            int p2 = random.nextInt(0, priTypeConfig.size());
            int s2 = random.nextInt(0, secTypeConfig.size());
            int p3 = random.nextInt(0, priTypeConfig.size());
            int s3 = random.nextInt(0, secTypeConfig.size());
            setAllowedConfig(new Pair<Integer, Integer>(p1, s1),
                    new Pair<Integer, Integer>(p2, s2),
                    new Pair<Integer, Integer>(p3, s3));
            for (Integer integer : renableTimers.keySet()) {
                renableTimers.put(integer, NUM_INIT);
            }
            nextSwitchAllowedTime += 10000;
        }
    }

    public ImmutableArray<Pair<Color, TextureRegion>> getAllowedTex() {
        return allowedTex;
    }

    private void rotate(BubbleComponent b, TransformComponent t, float deltaTime) {
        float nextRot = b.rotation + (rotationSpeed * deltaTime);
        final float cX = (getCenterX() - (b.width / 2));
        final float cY = (getCenterY() - b.height / 2);
        t.pX = cX + (b.radiusToCenter * MathUtils.cosDeg(nextRot));
        t.pY = cY + (b.radiusToCenter * MathUtils.sinDeg(nextRot));
        b.rotation = nextRot;
    }

    private void processConfigType(BubbleComponent b, RendererComponent r) {
        Preconditions.checkArgument(priTypeConfig.containsKey(b.firstType));
        Preconditions.checkArgument(secTypeConfig.containsKey(b.secondType));

        Array<TextureInfo> textureInfos = new Array<TextureInfo>();
        //Add the circle, then the tex, then the ring
        int p = b.firstType, s = b.secondType;
        float w = b.width, h = b.height;
        textureInfos.add(new TextureInfo(circle, w, h, 0, 0, priTypeConfig.get(p)));
        textureInfos.add(new TextureInfo(circleRim, w, h, 0, 0, Color.BLACK));
        textureInfos.add(new TextureInfo(secTypeConfig.get(s),
                w * TOF, h * TOF, w * (1.0f / 6.0f), h * (1.0f / 6.0f), Color.WHITE));
        r.textureInfos = new ImmutableArray<TextureInfo>(textureInfos);
    }


}
