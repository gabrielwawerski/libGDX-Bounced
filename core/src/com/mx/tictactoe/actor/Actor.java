package com.mx.tictactoe.actor;

import com.badlogic.gdx.math.Rectangle;

public class Actor extends com.badlogic.gdx.scenes.scene2d.Actor {
    protected Rectangle entity;
    private float entitySpeed = 1;

    public Actor() {
        entity = new Rectangle();
    }

    public boolean overlaps(Actor other) {
        return entity.overlaps(other.getEntity());
    }

    public void set(float x, float y) {
        setX(x);
        setY(y);
    }

    public float getX() {
        return entity.x;
    }

    public void setX(float x) {
        entity.x = x;
    }

    public float getY() {
        return entity.y;
    }

    public void setY(float y) {
        entity.y = y;
    }

    public float getEntityWidth() {
        return entity.width;
    }

    public void setEntityWidth(float width) {
        entity.width = width;
    }

    public float getEntityHeight() {
        return entity.height;
    }

    public void setEntityHeight(float height) {
        entity.height = height;
    }

    public float getEntitySpeed() {
        return entitySpeed;
    }

    public void setEntitySpeed(float entitySpeed) {
        this.entitySpeed = entitySpeed;
    }

    public Rectangle getEntity() {
        return entity;
    }

    public void dispose() {
        entity = null;
    }
}
