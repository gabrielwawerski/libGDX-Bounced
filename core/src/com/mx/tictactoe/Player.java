package com.mx.tictactoe;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import entity.Entity;
import com.mx.tictactoe.util.Config;
import com.mx.tictactoe.screen.GameScreen;

import static com.mx.tictactoe.screen.GameScreen.PIXELS_TO_METERS;

public class Player extends Entity {
    private Body body;
    private Texture texture;
    public final Sprite sprite;
    private final BodyDef bodyDef;


    public final float BIDIRECTIONAL_FORCE = 1f;
    public final float SINGLE_DIRECTIONAL_FORCE = 0.6f;
    private final float DOWN_MOVEMENT_BOOST = 0.15f;

    public static final long MAX_ENERGY = 550;
    public long energy = MAX_ENERGY;

    private final long ENERGY_DRAIN = 15;
    public final long ENERGY_RECOVERY_SPEED = 5;
    private boolean jumped = false;

    public Player(Texture texture) {
//        setEntityWidth(Config.PLAYER_WIDTH);
//        setEntityHeight(Config.PLAYER_HEIGHT);
        this.texture = texture;
        setEntitySpeed(1f);
        sprite = new Sprite(texture);

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                (sprite.getX() + sprite.getWidth() / 2) / GameScreen.PIXELS_TO_METERS,
                (sprite.getY() + sprite.getHeight() / 2) / GameScreen.PIXELS_TO_METERS
        );
    }

    public void update(float torque) {
        setX((body.getPosition().x * PIXELS_TO_METERS) - Config.PLAYER_WIDTH / 2f);
        setY((body.getPosition().y * PIXELS_TO_METERS) - Config.PLAYER_HEIGHT / 2f);
        sprite.setPosition(
                (body.getPosition().x * PIXELS_TO_METERS) - Config.PLAYER_WIDTH / 2f,
                (body.getPosition().y * PIXELS_TO_METERS) - Config.PLAYER_HEIGHT / 2f
        );
        body.applyTorque(torque, true);

        if (energy == MAX_ENERGY) {
            setJumped(false);
        } else if (energy < MAX_ENERGY) {
            energy += ENERGY_RECOVERY_SPEED;
        }
    }

    public float calcSpeed(float speed) {
        return speed * getEntitySpeed();
    }

    @Deprecated
    public void moveUpRight() {
        applyForce(calcSpeed(BIDIRECTIONAL_FORCE), calcSpeed(BIDIRECTIONAL_FORCE));
    }

    @Deprecated
    public void moveUpLeft() {
        applyForce(calcSpeed(-BIDIRECTIONAL_FORCE), calcSpeed(BIDIRECTIONAL_FORCE));
    }

    @Deprecated
    public void moveDownRight() {
        applyForce(calcSpeed(BIDIRECTIONAL_FORCE), calcSpeed(-BIDIRECTIONAL_FORCE));
    }

    @Deprecated
    public void moveDownLeft() {
        applyForce(calcSpeed(-BIDIRECTIONAL_FORCE), calcSpeed(-BIDIRECTIONAL_FORCE));
    }

    public void moveLeft() {
        applyForce(calcSpeed(-SINGLE_DIRECTIONAL_FORCE), 0);
    }

    public void moveRight() {
        applyForce(calcSpeed(SINGLE_DIRECTIONAL_FORCE), 0);
    }

    public void moveUp() {
        applyForce(0, 0);
    }

    public void moveDown() {
        applyForce(calcSpeed(DOWN_MOVEMENT_BOOST), calcSpeed(-SINGLE_DIRECTIONAL_FORCE));
    }

    public void jump() {
        if (energy > 0) {
            applyForce(0, calcSpeed(SINGLE_DIRECTIONAL_FORCE));
            drainEnergy();
        }
    }

    public void applyForce(float forceX, float forceY) {
        body.applyForce(new Vector2(forceX, forceY), body.getPosition(), true);
    }

    public void setLinearDamping(float linearDamping) {
        body.setLinearDamping(linearDamping);
    }

    private void drainEnergy() {
        energy -= ENERGY_DRAIN;
    }

    public void initBody(Body body) {
        this.body = body;
    }

    public BodyDef getBodyDef() {
        return bodyDef;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Body getBody() {
        return body;
    }

    public Texture getTexture() {
        return texture;
    }

    public void switchJumped() {
        jumped = !jumped;
    }

    public void setJumped(boolean jumped) {
        this.jumped = jumped;
    }

    public boolean didJump() {
        return jumped;
    }

    public long getJumpRecoverySpeed() {
        return ENERGY_RECOVERY_SPEED;
    }

    public long getJumpForceDrain() {
        return ENERGY_DRAIN;
    }
}
