package com.mx.tictactoe.core;

import com.badlogic.gdx.Gdx;
import com.mx.tictactoe.core.actor.EnergyBar;
import com.mx.tictactoe.util.Assets;

public final class GUI {
    private static GUI instance = null;
    private static GameWorld gameWorld_;
    private static EnergyBar energyBar;

    public static GUI getInstance() {
        if (instance == null) {
            instance = new GUI();
            energyBar = new EnergyBar();
        }
        return instance;
    }

    public void update() {
        energyBar.act(Gdx.graphics.getDeltaTime());
    }

    public void draw() {
        gameWorld_.getGame().batch.draw(Assets.ENERGY_BAR, energyBar.getActorX(), energyBar.getActorY());
    }

    public void setGameWorld(GameWorld gameWorld) {
        gameWorld_ = gameWorld;
    }

    private GUI() {
    }
}
