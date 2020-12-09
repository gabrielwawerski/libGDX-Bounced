package com.mx.tictactoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mx.tictactoe.util.Config;

public class Raindrop extends Entity {
    private Rectangle raindrop;

    public Raindrop(Texture texture, Vector2 playerPosition) {
        super(texture);
        setWidth(20);
        setHeight(20);
//        if (playerPosition.x < 50) {
//            setX(MathUtils.random(Gdx.graphics.getWidth() / 2 - MathUtils.random(0, 200)));
//        } else if (playerPosition.x > Gdx.graphics.getWidth() - 250) {
//            setX(MathUtils.random(0, Gdx.graphics.getWidth() / 2 - MathUtils.random(0, 200)));
//        }
        setX(MathUtils.random(0, com.mx.tictactoe.util.Config.WINDOW_WIDTH - 64));
        setY(Config.WINDOW_HEIGHT);
    }
}
