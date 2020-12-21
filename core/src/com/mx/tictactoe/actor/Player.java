package com.mx.tictactoe.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mx.tictactoe.core.GameWorld;
import com.mx.tictactoe.core.util.Config;
import com.mx.tictactoe.screen.GameScreen;
import com.mx.tictactoe.actor.interfaces.GameObject;

import static com.mx.tictactoe.screen.GameScreen.PIXELS_TO_METERS;
import static com.mx.tictactoe.core.util.Config.*;

public class Player extends Actor implements GameObject {
    private Body body;
    private Texture texture;
    public final Sprite sprite;
    private final BodyDef bodyDef;

    public final float BIDIRECTIONAL_FORCE = 1f;
    public final float SINGLE_DIRECTIONAL_FORCE = 0.57f;
    private final float DOWN_MOVEMENT_BOOST = 0.15f;

    public static final int MAX_ENERGY = 550;
    public int energy = MAX_ENERGY;

    private final long ENERGY_DRAIN = 15;
    public final long ENERGY_RECOVERY_SPEED = 5;
    private boolean jumped = false;

    public Player(Texture texture) {
        setEntityWidth(Config.PLAYER_WIDTH);
        setEntityHeight(Config.PLAYER_HEIGHT);
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

    private void handleEnergy() {
        if (energy < MAX_ENERGY) {
            energy += ENERGY_RECOVERY_SPEED;

            if (energy + ENERGY_RECOVERY_SPEED > MAX_ENERGY) {
                energy = MAX_ENERGY;
            }
        }
    }

    @Override
    public void update() {
        setX((body.getPosition().x * PIXELS_TO_METERS) - Config.PLAYER_WIDTH / 2f);
        setY((body.getPosition().y * PIXELS_TO_METERS) - Config.PLAYER_HEIGHT / 2f);
        body.applyTorque(GameWorld.TORQUE, true);

        handleEnergy();

        // handle more than one button pressed at one time
        if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveUp();
            moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveDown();
            moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveDown();
            moveRight();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveUp();
            moveRight();
        }

        // handle moving
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveDown();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveRight();
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveUp();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !didJump()) {
            jump();
        }
    }

    public float calcSpeed(float speed) {
        return speed * getEntitySpeed();
    }

    public void moveLeft() {
        applyForce(calcSpeed(-SINGLE_DIRECTIONAL_FORCE), 0);
    }

    public void moveRight() {
        applyForce(calcSpeed(SINGLE_DIRECTIONAL_FORCE), 0);
    }

    public void moveUp() {
        applyForce(0, calcSpeed(DOWN_MOVEMENT_BOOST));
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

    private void drainEnergy() {
        if (energy < 0) {
            energy = 0;
        } else if (energy > MAX_ENERGY) {
            energy = MAX_ENERGY;
        } else
            energy -= ENERGY_DRAIN;
    }

    @Override
    public void init(World world) {
        body = world.createBody(getBodyDef());
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                16f / PIXELS_TO_METERS,
                16f / PIXELS_TO_METERS
        );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = PLAYER_DENSITY;
        fixtureDef.friction = PLAYER_FRICTION;
        fixtureDef.restitution = PLAYER_RESTITUTION;
        Fixture fixture = body.createFixture(fixtureDef);
        // shape no longer needed
        shape.dispose();

        getBody().setLinearVelocity(2f, 2f);
//        getSprite().setRotation((float) Math.toDegrees(getBody().getAngle()));
        getBody().setLinearDamping(1.5f);
        getBody().setAngularDamping(0.5f);
    }

    @Override
    public void dispose() {
        super.dispose();

    }

    @Override
    public void setX(float x) {
        entity.x = x;
        sprite.setX(x);
    }

    @Override
    public void setY(float y) {
        entity.y = y;
        sprite.setY(y);
    }

    public void setLinearDamping(float linearDamping) {
        body.setLinearDamping(linearDamping);
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
