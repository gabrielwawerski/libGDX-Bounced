package com.mx.tictactoe;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
    private Texture texture;
    private Rectangle entity;

    private static final float ENTITY_SPEED = 100;

    public Entity(Texture texture) {
        this.texture = texture;
        entity = new Rectangle();
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getEntity() {
        return entity;
    }

    public boolean overlaps(Rectangle other) {
        return entity.overlaps(other);
    }

    public boolean overlaps(Entity other) {
        return entity.overlaps(other.getEntity());
    }

    public float getX() {
        return entity.x;
    }

    public void setX(float x) {
        entity.x = x;
    }

    public void traverseX(float targetX, float speed) {
        while(entity.x < targetX || entity.x > targetX) {
            // handle moving left
            if (targetX < entity.x) {
                setX(entity.x - ENTITY_SPEED * speed);
            }

            // handle moving right
            else if (targetX > entity.x) {
                setX(entity.x + ENTITY_SPEED * speed);
            }
        }
    }

    public float getY() {
        return entity.y;
    }

    public void setY(float y) {
        entity.y = y;
    }

    public float getWidth() {
        return entity.width;
    }

    public void setWidth(float width) {
        entity.width = width;
    }

    public float getHeight() {
        return entity.height;
    }

    public void setHeight(float height) {
        entity.height = height;
    }

    public void dispose() {
        texture.dispose();
    }
}