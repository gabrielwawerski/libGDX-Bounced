package com.mx.tictactoe.core.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mx.tictactoe.util.Assets;

public class EnergyBar extends Actor {
    private TextureRegion textureRegion = new TextureRegion(
            Assets.ENERGY_BAR, Assets.ENERGY_BAR.getWidth(), Assets.ENERGY_BAR.getHeight());

    float actorX;
    float actorY;
    public boolean started = false;
    private boolean flipped;

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((EnergyBar) event.getTarget()).started = true;
                return true;
            }
        });

    }

    private void applyEffect() {
        actorY += 50;
        textureRegion.flip(false, true);
        flipped = true;
    }

    @Override
    public void act(float delta){
        if(started && !flipped){
            applyEffect();
        }
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public float getActorX() {
        return actorX;
    }

    public float getActorY() {
        return actorY;
    }
}
