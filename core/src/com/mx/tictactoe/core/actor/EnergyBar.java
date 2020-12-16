package com.mx.tictactoe.core.actor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mx.tictactoe.Player;
import com.mx.tictactoe.util.Assets;

public class EnergyBar extends Actor {
    private TextureRegion textureRegion = new TextureRegion(
            Assets.ENERGY_BAR, Assets.ENERGY_BAR.getWidth(), Assets.ENERGY_BAR.getHeight());

    private Player player;

    float actorX;
    float actorY;
    public boolean started = false;
    public boolean scaled = false;
    public boolean isOver = false;

    public EnergyBar(float actorX, float actorY, Player player) {
        setBounds(actorX, actorY, Assets.ENERGY_BAR.getWidth(), Assets.ENERGY_BAR.getHeight());
        this.player = player;

        this.actorX = actorX;
        this.actorY = actorY;
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((EnergyBar) event.getTarget()).started = true;
                return true;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    player.jump();
                }
                return true;
            }
        });

        addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                ((EnergyBar) event.getTarget()).isOver = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                ((EnergyBar) event.getTarget()).isOver = false;
            }
        });

    }

    // TODO figure me out
    @Override
    public void act(float delta) {
        super.act(delta);

        if (started) {
            System.out.println("acted out!");
            started = false;
        }

        if (isOver) {
            if (!scaled) {
//            sizeBy(0.25f);
                System.out.println("is Over! SCAILING");
                setSize(1.5f, 1.5f);
                scaled = true;
            }
        } else if (!isOver && scaled) {
            setWidth(300);
            setHeight(80);
            scaled = false;
        }
    }

    private void onOver() {
        System.out.println("onOver!");

    }

    private void onExit() {
        System.out.println("onExit!");

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

    public float getActorX() {
        return actorX;
    }

    public float getActorY() {
        return actorY;
    }
}
