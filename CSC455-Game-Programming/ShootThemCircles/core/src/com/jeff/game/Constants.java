package com.jeff.game;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.StringBuilder;
import com.google.common.base.Preconditions;

/**
 * Class to hold constants
 */
public class Constants {

    public static final int WINDOW_HEIGHT = 800;
    public static final int WINDOW_WIDTH = 500;
    public static final String WINDOW_TITLE = "Black Ops:Shoot em";

    public static final String FLDR_IMAGES = "images";
    public static final String FLDR_MUSIC = "music";
    public static final String FLDR_ATLASES = "atlases";
    public static final String FLDR_SOUNDS = "sounds";
    public static final String FLDR_MISC = "misc";
    public static final String FLDR_SHOOT = "shoot";
    public static final String FLDR_UI = "ui";
    public static final String FLDR_WHEEL = "wheel";
    public static final String FLDR_WORLD = "world";

    /**
     * Concats uri from strings.
     *
     * @param names
     *         Strings.
     * @return Uri.
     * @throws NullPointerException
     *         if params are null.
     * @throws IllegalArgumentException
     *         reasons.
     */
    public static String uri(String... names) {
        Preconditions.checkNotNull(names);
        Preconditions.checkArgument(names.length > 0);
        StringBuilder builder = new StringBuilder();
        for (String name : names) {
            if(builder.length > 0)
                builder.append("/");
            builder.append(name);
        }
        return builder.toString();
    }

}
