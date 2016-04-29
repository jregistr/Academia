package com.jeff.game.castlesmack.util.constant;


public class Constants {

    public class TexConstants {

        public static final String ISLAND = "island.png";
        public static final String HOUSE = "house.png";
        public static final String PIPE = "pipe.png";
        public static final String ROCK = "rock.png";

    }

    public static final float M_TO_PIX = 16;

    public static final float PIX_TO_M = 1.0f / M_TO_PIX;

    public static final float WIDTH_PLAYER_ISLAND = 15;
    public static final float HEIGHT_PLAYER_ISLAND = 6.5f;

    public static float meterToPix(float meter) {
        return meter * M_TO_PIX;
    }

    public static float pixelToMeter(float pixel) {
        return pixel * PIX_TO_M;
    }

}
