package entity;

import com.badlogic.gdx.math.Rectangle;

public class Entity {
    private Rectangle entity;
    private float entitySpeed = 1;

    public Entity() {
        entity = new Rectangle();
    }

    public boolean overlaps(Entity other) {
        return entity.overlaps(other.getEntity());
    }

    public boolean overlaps(Rectangle other) {
        return entity.overlaps(other);
    }

    public Rectangle getEntity() {
        return entity;
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

    public void dispose() {
        entity = null;
    }
}
