package com.mx.tictactoe.util;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mx.tictactoe.Raindrop;

public class RainDropper {
    public Array<Raindrop> raindrops;

    public static long timeSinceLastDrop;
    public static final long TIME_UNTIL_NEXT_SPAWN = 400000000;

    public RainDropper() {
        raindrops = new Array<>();
    }

    public void spawnRaindrop() {
        raindrops.add(new Raindrop(Assets.RAINDROP_TEXTURE));
        timeSinceLastDrop = TimeUtils.nanoTime();
    }
}
