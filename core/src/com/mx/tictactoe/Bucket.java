package com.mx.tictactoe;

import com.badlogic.gdx.graphics.Texture;
import com.mx.tictactoe.util.Config;

public class Bucket extends Entity {
    public Bucket(Texture texture) {
        super(texture);
        setWidth(Config.BUCKET_RECTANGLE_WIDTH);
        setHeight(Config.BUCKET_RECTANGLE_HEIGHT);
        setX(Config.BUCKET_POS_X);
        setY(Config.BUCKET_POS_Y);
    }
}
