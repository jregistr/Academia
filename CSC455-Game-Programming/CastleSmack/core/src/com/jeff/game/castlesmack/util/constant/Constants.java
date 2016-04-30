package com.jeff.game.castlesmack.util.constant;


public class Constants {

    public class TexConstants {

        public static final String ISLAND = "island.png";
        public static final String HOUSE = "house.png";
        public static final String PIPE = "pipe.png";
        public static final String ROCK = "rock.png";

    }

    public static final float M_TO_PIX = 16;

    private static final float PIX_TO_M = 1.0f / M_TO_PIX;

    public static final float WIDTH_PLAYER_ISLAND = 15;
    public static final float HEIGHT_PLAYER_ISLAND = 6.5f;

    public static final float WIDTH_PLAYER_HOUSE = WIDTH_PLAYER_ISLAND * (3.0f / 4.0f);
    public static final float HEIGHT_PLAYER_HOUSE = 7.3f;

    public static final float WIDTH_PLAYER_GUN_ISLAND = 7;
    public static final float HEIGHT_PLAYER_GUN_ISLAND = 5.5f;

    public static final float WIDTH_PLAYER_GUN = 2;
    public static final float HEIGHT_PLAYER_GUN = 10;

    public static final float PLAYER_HOUSE_MAX_HP_START = 1000;
    public static final float PLAYER_GUN_MAX_HP_START = 400;

    public static final float PLAYER_GUN_MAX_FORCE_START = 100;
    public static final float PLAYER_PROJECTILE_DAMAGE_START = 200;
    public static final float PLAYER_PROJECTILE_WEIGHT = 100;

    public static float meterToPix(float meter) {
        return meter * M_TO_PIX;
    }

    public static float pixelToMeter(float pixel) {
        return pixel * PIX_TO_M;
    }

}
