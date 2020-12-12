package com.mx.tictactoe.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mx.tictactoe.Bucket;
import com.mx.tictactoe.util.RainDropper;
import com.mx.tictactoe.Raindrop;
import com.mx.tictactoe.screen.GameScreen;
import com.mx.tictactoe.util.Config;

import java.util.Iterator;

import static com.mx.tictactoe.screen.GameScreen.PIXELS_TO_METERS;

public class GameWorld  implements Disposable {
    private final GameScreen gameScreen;
    private final DropGame game;

    private final World world;
    public Bucket bucket;
    private RainDropper rainDropper;

    private int score;

    public GameWorld(DropGame game, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.game = game;
        // todo default bucket implementation
//        setBucket(defaultBucket);
        world = new World(new Vector2(0, -3), true);

        rainDropper = new RainDropper();
    }

    public void update() {
        bucket.update();

        if (Config.SPAWN_RAINDROPS) {
            for (Iterator<Raindrop> iter = rainDropper.raindrops.iterator(); iter.hasNext(); ) {
                Raindrop raindrop = iter.next();

                raindrop.setY(raindrop.getY() - 200 * Gdx.graphics.getDeltaTime());
                // remove raindrop if falls from screen
                if (raindrop.getY() + 64 < 0) {
                    iter.remove();
                }

                // remove raindrop if bucket collected it
                if (getBucketSprite().getBoundingRectangle().overlaps(raindrop.getEntity())) {
                    game.assets.DROP_SOUND.play();
                    iter.remove();
                    addScore();
                }
            }
        }
    }



    public void step() {
        world.step(1f / 60f, 1, 3);
    }

    public void spawnRaindrop() {
        rainDropper.spawnRaindrop();
    }

    public Bucket createBucket(Texture bucketTexture) {
        return bucket = new Bucket(bucketTexture);
    }

    private void initBucket() {
        bucket.init(world.createBody(bucket.getBodyDef()));

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                bucket.sprite.getWidth() / 2 / PIXELS_TO_METERS,
                bucket.sprite.getHeight() / 2 / PIXELS_TO_METERS
        );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.35f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.4f;
        Fixture fixture = bucket.getBody().createFixture(fixtureDef);
        shape.dispose();
        bucket.getBody().setLinearVelocity(2f, 2f);
    }

    public void init(Bucket bucket) {
        this.bucket = bucket;
        initBucket();
        resetScore();
    }

    public World getWorld() {
        return world;
    }

    public Sprite getBucketSprite() {
        return bucket.getSprite();
    }

    public Array<Raindrop> getRaindrops() {
        return rainDropper.raindrops;
    }

    public void addScore() {
        score++;
    }

    public void resetScore() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void dispose() {
        bucket.dispose();
        game.font.dispose();
        gameScreen.dispose();
    }
}
