package com.mx.tictactoe.actor.interfaces;

import com.badlogic.gdx.physics.box2d.World;

public interface GameObject {
    void init(World world);

    void update();

    void dispose();
}
