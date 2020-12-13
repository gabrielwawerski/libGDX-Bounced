package com.mx.tictactoe.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mx.tictactoe.util.Logger;

public final class Config {
    // window size
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 480;

    //======================

    // bucket settings
    public static final float PLAYER_RESTITUTION = 0.4f;
    public static final float PLAYER_FRICTION = 0.2f;
    public static final float PLAYER_DENSITY = 0.35f;

    // bucket size
    public static final int PLAYER_WIDTH = 64;
    public static final int PLAYER_HEIGHT = 64;

    // bucket position
    public static final int PLAYER_POS_X = Gdx.graphics.getWidth() / 2;
    public static final int PLAYER_POS_Y = PLAYER_HEIGHT / 2;

    //=======================

    // spawn settings
    public static boolean RAINDROPS_SPAWN = true;
    public static boolean BUCKET_SPAWN = true;

    // draw settings
    public static boolean RAINDROPS_DRAW = true;
    public static boolean PLAYER_DRAW = true;

    // sound settings (0 to 1)
    public static final float RAIN_VOLUME = 0.15f;
    public static final float DROP_SOUND_VOLUME = 0.45f;

    //===================================================

    /** {@link Logger#setLogLevel(int)}  */
    public static final int DEFAULT_LOG_LEVEL = Logger.LOG_DEBUG;
}