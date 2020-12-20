package com.mx.tictactoe.actor.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.mx.tictactoe.actor.Actor;
import com.mx.tictactoe.core.util.Config;

public class Raindrop extends Actor {
    public Raindrop(Texture texture) {
        setEntityWidth(Config.RAINDROP_WIDTH);
        setEntityHeight(Config.RAINDROP_HEIGHT);
        setEntitySpeed(Config.RAINDROP_SPEED);
        setX(MathUtils.random(0, Config.WINDOW_WIDTH - 64));
        setY(Config.WINDOW_HEIGHT);
    }
}
