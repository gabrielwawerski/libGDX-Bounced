package com.mx.tictactoe.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.mx.tictactoe.Player;
import com.mx.tictactoe.util.Assets;
import com.mx.tictactoe.util.RainDropper;
import com.mx.tictactoe.Raindrop;
import com.mx.tictactoe.screen.GameScreen;
import com.mx.tictactoe.util.Config;

import java.util.Iterator;

import static com.mx.tictactoe.screen.GameScreen.PIXELS_TO_METERS;
import static com.mx.tictactoe.util.Config.*;

public class GameWorld  implements Disposable {
    private final GameScreen gameScreen;
    private final DropGame game;
    private final World world;
    private final RainDropper rainDropper;

    public Player player;
    private int score;

    private static final float TORQUE = 1f;
    private static final float LINEAR_DAMPING = 2.5f;
    /** Force at which to bounce player in the opposite direction, if he exceeds set world bounds */
    private static final float BOUNDS_REPEL_FRC = 2f;

    public GameWorld(DropGame game, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.game = game;
        world = new World(new Vector2(0, -3), true);
        rainDropper = new RainDropper();

        // todo default player implementation
//        createPlayer(player)

        Assets.RAIN_MUSIC.setLooping(true);
        Assets.RAIN_MUSIC.setVolume(Config.RAIN_VOLUME);
    }

    public void update() {
        world.step(1f / 60f, 1, 3);
        player.update(TORQUE);
        player.getBody().setLinearDamping(1.6f);
        player.getBody().setAngularDamping(0.5f);

        if (Config.RAINDROPS_SPAWN) {
            for (Iterator<Raindrop> iter = rainDropper.raindrops.iterator(); iter.hasNext(); ) {
                Raindrop raindrop = iter.next();

                raindrop.setY(raindrop.getY() - raindrop.getEntitySpeed() * Gdx.graphics.getDeltaTime());
                // remove raindrop if it falls off screen
                if (raindrop.getY() + 64 < 0) {
                    iter.remove();
                }

                // remove raindrop if bucket collected it
                if (raindrop.overlaps(player.getEntity())) {
                    iter.remove();
                    addScore();
                }
            }

            if (TimeUtils.nanoTime() - RainDropper.timeSinceLastDrop > RainDropper.TIME_UNTIL_NEXT_SPAWN) {
                spawnRaindrops();
            }
        }

        worldBounds();
    }

    /**
     * World constraints
     */
    private void worldBounds() {
        // left bound
        if (player.getSprite().getX() - 5 <= 0) {
            player.applyForce(BOUNDS_REPEL_FRC, 0);
            player.setLinearDamping(LINEAR_DAMPING);
        }

        // right bound
        if (player.getSprite().getX() >= Gdx.graphics.getWidth() - player.getSprite().getWidth() / 2 - 5) {
//            body.setLinearVelocity(-1.5f, body.getLinearVelocity().y);
            player.applyForce(-BOUNDS_REPEL_FRC, 0);
            player.setLinearDamping(LINEAR_DAMPING);
        }

        // up bound
        if (player.getSprite().getY() + player.getSprite().getHeight() / 2 - 2 >= Gdx.graphics.getHeight()) {
            player.applyForce(0, -BOUNDS_REPEL_FRC);
            player.setLinearDamping(LINEAR_DAMPING);
        }

        // down bound
        if (player.getSprite().getY() - 2 <= 0) {
            player.applyForce(0, BOUNDS_REPEL_FRC);
            player.setLinearDamping(LINEAR_DAMPING);
        }
    }

    /**
     * Android handle
     */
    public void onShow() {
        Assets.RAIN_MUSIC.play();
    }

    public void spawnRaindrops() {
        rainDropper.spawnRaindrop();
    }

    public void init(Texture texture) {
        createPlayer(texture);
        resetScore();
    }

    public void createPlayer(Texture bucketTexture) {
        player = new Player(bucketTexture);
        initPlayer();
    }

    private void initPlayer() {
        player.init(world.createBody(player.getBodyDef()));

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                player.sprite.getWidth() / 2 / PIXELS_TO_METERS,
                player.sprite.getHeight() / 2 / PIXELS_TO_METERS
        );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = PLAYER_DENSITY;
        fixtureDef.friction = PLAYER_FRICTION;
        fixtureDef.restitution = PLAYER_RESTITUTION;
        Fixture fixture = player.getBody().createFixture(fixtureDef);
        shape.dispose();
        player.getBody().setLinearVelocity(2f, 2f);

        player.getSprite().setRotation((float) Math.toDegrees(player.getBody().getAngle()));
        player.getBody().setLinearDamping(1.5f);
        player.getBody().setAngularDamping(0.5f);
    }

    public World getWorld() {
        return world;
    }

    public Sprite getPlayerSprite() {
        return player.getSprite();
    }

    public Array<Raindrop> getRaindrops() {
        return rainDropper.raindrops;
    }

    public void addScore() {
        game.assets.DROP_SOUND.play();
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
        world.dispose();
        player.dispose();
        game.font.dispose();
        gameScreen.dispose();
    }
}
