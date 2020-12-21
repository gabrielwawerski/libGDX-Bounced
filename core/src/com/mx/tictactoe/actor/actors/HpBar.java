package com.mx.tictactoe.actor.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mx.tictactoe.actor.Actor;
import com.mx.tictactoe.actor.Player;
import com.mx.tictactoe.core.util.assets.Assets;

public class HpBar extends Actor {
    private TextureRegion textureRegion = new TextureRegion(
            Assets.HP_BAR, Assets.HP_BAR.getWidth(), Assets.HP_BAR.getHeight());
    private Player player;

    public HpBar(float actorX, float actorY, Player player) {
        this.player = player;
        set(actorX, actorY);
        setBounds(actorX, actorY, Assets.HP_BAR.getWidth(), Assets.HP_BAR.getHeight());
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((EnergyBar) event.getTarget()).isTouched = true;
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), player.health / 3f, getHeight(),
                getScaleX(), getScaleY(), getRotation());
        setBounds(getX(), getY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }

    @Override
    public void dispose() {
        super.dispose();
        textureRegion = null;
    }
}
