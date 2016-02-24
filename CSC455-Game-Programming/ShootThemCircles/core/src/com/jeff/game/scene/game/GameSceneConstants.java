package com.jeff.game.scene.game;

import com.badlogic.gdx.Gdx;

import static com.jeff.game.Constants.*;

/**
 * Class to hold constants for game scene.
 */
public class GameSceneConstants {

    protected static final String CIRCLE_FILL = uri(FLDR_IMAGES, FLDR_WHEEL, "CircleFill.png");
    protected static final String CIRCLE_FILL_FADED = uri(FLDR_IMAGES, FLDR_WHEEL, "CircleFillFade.png");
    protected static final String CIRCLE_OUTLINE = uri(FLDR_IMAGES, FLDR_WHEEL, "CircleOutline.png");
    protected static final String LINE = uri(FLDR_IMAGES, FLDR_WHEEL, "line.png");
    protected static final String OBJ_1 = uri(FLDR_IMAGES, FLDR_WHEEL, "obj_01.png");
    protected static final String OBJ_2 = uri(FLDR_IMAGES, FLDR_WHEEL, "obj_02.png");
    protected static final String OBJ_3 = uri(FLDR_IMAGES, FLDR_WHEEL, "obj_03.png");
    protected static final String OBJ_4 = uri(FLDR_IMAGES, FLDR_WHEEL, "obj_04.png");
    protected static final String OBJ_5 = uri(FLDR_IMAGES, FLDR_WHEEL, "obj_05.png");
    protected static final String CANNON = uri(FLDR_IMAGES, FLDR_SHOOT, "gun.png");
    protected static final String ROCKET = uri(FLDR_IMAGES, FLDR_SHOOT, "rocket.png");
    protected static final String SKY = uri(FLDR_IMAGES, FLDR_WORLD, "sky.png");
    protected static final String GROUND = uri(FLDR_IMAGES, FLDR_WORLD, "ground.png");
    protected static final String CLOUD1 = uri(FLDR_IMAGES, FLDR_WORLD, "cloud_1.png");
    protected static final String CLOUD2 = uri(FLDR_IMAGES, FLDR_WORLD, "cloud_2.png");
    protected static final String CLOUD3 = uri(FLDR_IMAGES, FLDR_WORLD, "cloud_3.png");
    protected static final String CLOUD4 = uri(FLDR_IMAGES, FLDR_WORLD, "cloud_4.png");

    protected static final String BEAR = uri(FLDR_IMAGES, FLDR_WHEEL, "bear.png");
    protected static final String FEELS = uri(FLDR_IMAGES, FLDR_WHEEL, "feels.png");
    protected static final String FOREVER = uri(FLDR_IMAGES, FLDR_WHEEL, "forever.png");
    protected static final String HEAVY = uri(FLDR_IMAGES, FLDR_WHEEL, "heavy.png");
    protected static final String KERMIT = uri(FLDR_IMAGES, FLDR_WHEEL, "kermit.png");
    protected static final String PEPE = uri(FLDR_IMAGES, FLDR_WHEEL, "pepe.png");
    protected static final String TROLL = uri(FLDR_IMAGES, FLDR_WHEEL, "troll.png");

    protected static final String SHOOT = uri(FLDR_MUSIC, "gun.mp3");
    protected static final String BLAST = uri(FLDR_MUSIC, "blast.mp3");

    public static final class RingConstants{

        protected static final float OUTER_WIDTH = 110, OUTER_HEIGHT = 110;
        protected static final float MIDDLE_WIDTH = 85, MIDDLE_HEIGHT = 85;
        protected static final float INNER_WIDTH = 40, INNER_HEIGHT = 40;

        protected static final float RADIUS_OUTER = 150;
        protected static final float RADIUS_MIDDLE = 125;
        protected static final float RADIUS_INNER = 60;

        public static float getCenterX(){
            return Gdx.graphics.getWidth() / 2.0f;
        }

        public static float getCenterY(){
            return (Gdx.graphics.getHeight() / 1.8f) + RADIUS_OUTER;
        }

    }

}
