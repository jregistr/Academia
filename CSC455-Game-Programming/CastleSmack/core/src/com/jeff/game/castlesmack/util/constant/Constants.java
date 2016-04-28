package com.jeff.game.castlesmack.util.constant;


public class Constants {

    public static final float M_TO_PIX = 16;

    public static final float PIX_TO_M = 1.0f / M_TO_PIX;

    public static float meterToPix(float meter) {
        return meter * M_TO_PIX;
    }

    public static float pixelToMeter(float pixel) {
        return pixel * PIX_TO_M;
    }

}
