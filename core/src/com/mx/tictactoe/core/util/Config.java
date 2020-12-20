package com.mx.tictactoe.core.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public final class Config {
    // window size
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 480;

    // World settings
    public static final int GRAVITY_X = 0;
    public static final int GRAVITY_Y = -3;
    public static final float TIME_STEP = 1f / 60f;
    public static final int VELOCITY_ITERATIONS = 1;
    public static final int POSITION_ITERATIONS = 1;


    //======================
    // PLAYER SETTINGS

    // density, usually in kg/m^2.
    public static final float PLAYER_DENSITY = 0.6f;
    // friction coefficient, usually in the range [0,1]. ?
    public static final float PLAYER_FRICTION = 0.2f;
    // restitution (elasticity), usually in the range [0,1].
    public static final float PLAYER_RESTITUTION = 0.4f;
    // player size
    public static final float PLAYER_WIDTH = 128f / 2f;
    public static final float PLAYER_HEIGHT = 128f / 2f;
    // player position
    public static final float PLAYER_POS_X = Gdx.graphics.getWidth() / 2f;
    public static final float PLAYER_POS_Y = PLAYER_HEIGHT / 2f;

    //=======================

    // raindrops settings
    public static final float RAINDROP_SPEED = 300f;
    public static final float RAINDROP_WIDTH = 48f;
    public static final float RAINDROP_HEIGHT = 48f;

    //=======================

    // spawn settings
    public static boolean RAINDROPS_SPAWN = true;
    public static boolean BUCKET_SPAWN = true;

    // draw settings
    public static boolean RAINDROPS_DRAW = true;
    public static boolean PLAYER_DRAW = true;

    // sound settings (0 to 1)
    public static final float RAIN_VOLUME = 0.15f;
    public static final float DROP_SOUND_VOLUME = 0f;

    //===================================================

    /** {@link Logger#setLogLevel(int)}  */
    public static final int DEFAULT_LOG_LEVEL = Logger.LOG_DEBUG;
}