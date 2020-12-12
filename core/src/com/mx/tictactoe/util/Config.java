package com.mx.tictactoe.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mx.tictactoe.util.Logger;

public final class Config {
    // window size
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 480;

    /** {@link Logger#setLogLevel(int)}  */
    public static final int DEFAULT_LOG_LEVEL = Logger.LOG_DEBUG;

    public static final int BUCKET_WIDTH = 64;
    public static final int BUCKET_HEIGHT = 64;

    public static final int BUCKET_POS_X = Gdx.graphics.getHeight() / 2;
    public static final int BUCKET_POS_Y = Gdx.graphics.getWidth() / 2;

    // spawn settings
    public static boolean SPAWN_RAINDROPS = true;
    // draw settings
    public static boolean DRAW_BUCKET = true;

    /** from 0 to 1 */
    public static final float DROP_SOUND_VOLUME = 0.45f;
}