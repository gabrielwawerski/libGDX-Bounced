package com.mx.tictactoe.actor.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mx.tictactoe.actor.Player;
import com.mx.tictactoe.core.util.assets.Assets;

public class EnergyBar extends com.mx.tictactoe.actor.Actor {
    private TextureRegion textureRegion = new TextureRegion(
            Assets.ENERGY_BAR, Assets.ENERGY_BAR.getWidth(), Assets.ENERGY_BAR.getHeight());

    private Player player;

    public boolean isTouched = false;
    public boolean scaled = false;
    public boolean isOver = false;

    public EnergyBar(float actorX, float actorY, Player player) {
        this.player = player;
        set(actorX, actorY);
        setBounds(actorX, actorY, Assets.ENERGY_BAR.getWidth(), Assets.ENERGY_BAR.getHeight());
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

        addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                System.out.println("in!");
                ((EnergyBar) event.getTarget()).isOver = true;

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                System.out.println("out!");
                ((EnergyBar) event.getTarget()).isOver = false;
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isTouched) {
            System.out.println("acted out!");
            isTouched = false;
        }

        if (isOver) {
            if (!scaled) {
                scaleBy(1f);
                System.out.println("isOver! SCAILING");
                scaled = true;
            }
        } else if (!isOver && scaled) {
            setScale(1f, 1f);
            scaled = false;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), player.energy / 3f, getHeight(),
                getScaleX(), getScaleY(), getRotation());
        setBounds(getX(), getY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }
}
