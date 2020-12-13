package com.mx.tictactoe;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import entity.Entity;
import com.mx.tictactoe.util.Config;

public class Raindrop extends Entity {
    public Raindrop(Texture texture) {
        setEntityWidth(64);
        setEntityHeight(64);
        setEntitySpeed(150f);
        setX(MathUtils.random(0, Config.WINDOW_WIDTH - 64));
        setY(Config.WINDOW_HEIGHT);
    }
}
