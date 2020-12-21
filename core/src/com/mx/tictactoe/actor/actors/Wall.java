package com.mx.tictactoe.actor.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.mx.tictactoe.actor.Actor;
import com.mx.tictactoe.actor.interfaces.GameObject;
import com.mx.tictactoe.screen.GameScreen;

public class Wall extends Actor implements GameObject {
    private Body body;
    private Texture texture;
    public final Sprite sprite;
    private final BodyDef bodyDef;
    private PolygonShape shape;

    public Wall(float width, float height, float x, float y) {
        initShape(width, height);
        texture = new Texture(Gdx.files.internal("player.png"));
        sprite = new Sprite(texture);
        sprite.setRegionWidth(0);
        sprite.setRegionHeight(0);

        sprite.setX(x / GameScreen.PIXELS_TO_METERS);
        sprite.setY(y / GameScreen.PIXELS_TO_METERS);
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(sprite.getX(), sprite.getY());
    }

    private void initShape(float x, float y) {
        shape = new PolygonShape();
        shape.setAsBox(x / GameScreen.PIXELS_TO_METERS, y / GameScreen.PIXELS_TO_METERS);
        set(x / GameScreen.PIXELS_TO_METERS, y / GameScreen.PIXELS_TO_METERS);
    }

    @Override
    public void init(World world) {
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.1f;
        Fixture fixture = body.createFixture(fixtureDef);
    }

    private void set() {
        entity.x = body.getPosition().x;
        entity.y = body.getPosition().y;
    }

    @Override
    public void update() {
        set();
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

    @Override
    public void dispose() {
        super.dispose();
        texture.dispose();
        shape.dispose();
        body = null;

    }
}
