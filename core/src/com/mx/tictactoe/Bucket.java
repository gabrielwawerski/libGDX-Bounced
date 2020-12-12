package com.mx.tictactoe;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mx.tictactoe.core.Entity;
import com.mx.tictactoe.util.Config;
import com.mx.tictactoe.screen.GameScreen;

import static com.mx.tictactoe.screen.GameScreen.PIXELS_TO_METERS;

public class Bucket extends Entity {
    private Body body;
    public final Sprite sprite;

    public static final long JUMP_FORCE_DRAIN = 15;
    public static final long JUMP_RECOVERY_SPEED = 5;

    public static final long MAX_JUMP_TIME = 550;
    public long jumpTime = MAX_JUMP_TIME;
    private final BodyDef bodyDef;


    public Bucket(Texture texture) {
        super(texture);
        setWidth(Config.BUCKET_WIDTH);
        setHeight(Config.BUCKET_HEIGHT);
        setX(Config.BUCKET_POS_X);
        setY(Config.BUCKET_POS_Y);
        sprite = new Sprite(getTexture());

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                (sprite.getX() + sprite.getWidth() / 2) / GameScreen.PIXELS_TO_METERS,
                (sprite.getY() + sprite.getHeight() / 2) / GameScreen.PIXELS_TO_METERS
        );
    }

    public void update() {
        sprite.setPosition(
                (body.getPosition().x * PIXELS_TO_METERS) - Config.BUCKET_WIDTH / 2f,
                (body.getPosition().y * PIXELS_TO_METERS) - Config.BUCKET_HEIGHT / 2f
        );
    }

    public void jump() {
        if (jumpTime > 0) {
            body.applyForce(new Vector2(0, 0.6f), body.getPosition(), true);
            jumpTime -= Bucket.JUMP_FORCE_DRAIN;
        }
    }

    public void init(Body body) {
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
}
