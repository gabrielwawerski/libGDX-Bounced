package com.mx.tictactoe.core.util;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mx.tictactoe.actor.actors.Raindrop;
import com.mx.tictactoe.core.util.assets.Assets;

public class RainSpawner {
    public Array<Raindrop> raindrops;
    private boolean isSpawning = false;

    public static long timeSinceLastDrop;
    public static final long TIME_UNTIL_NEXT_SPAWN = 260000000;

    public RainSpawner() {
        raindrops = new Array<>();
    }

    public void spawnRaindrop() {
        raindrops.add(new Raindrop(Assets.RAINDROP_TEXTURE));
        timeSinceLastDrop = TimeUtils.nanoTime();
        isSpawning = true;
    }

    public boolean isSpawning() {
        return isSpawning;
    }
}
