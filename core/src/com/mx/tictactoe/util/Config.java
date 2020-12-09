package com.mx.tictactoe.util;

import com.badlogic.gdx.Gdx;

public final class Config {
    // window size
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 480;

    /** {@link Logger#setLogLevel(int)}  */
    public static final int DEFAULT_LOG_LEVEL = Logger.LOG_DEBUG;

    public static final int BUCKET_RECTANGLE_WIDTH = 32;
    public static final int BUCKET_RECTANGLE_HEIGHT = 32;

    public static final int BUCKET_POS_X = Gdx.graphics.getHeight() / 2;
    public static final int BUCKET_POS_Y = Gdx.graphics.getWidth() / 2;

    // raindrop spawning controls
    public static final boolean SPAWN_RAINDROPS = true;
}