package com.mx.tictactoe.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.mx.tictactoe.DropGame;
import com.mx.tictactoe.actor.player.Player;
import com.mx.tictactoe.core.ui.UI;
import com.mx.tictactoe.core.util.Score;
import com.mx.tictactoe.core.util.assets.Assets;
import com.mx.tictactoe.core.util.RainSpawner;
import com.mx.tictactoe.actor.actors.Raindrop;
import com.mx.tictactoe.screen.GameScreen;
import com.mx.tictactoe.core.util.Config;
import com.mx.tictactoe.actor.interfaces.GameObject;
import com.mx.tictactoe.actor.actors.Wall;

import java.util.Iterator;

public class GameWorld implements Disposable {
    private final GameScreen gameScreen;
    private final com.mx.tictactoe.DropGame game;
    private final World world;
    private final RainSpawner rainSpawner;
    public Player player;
    public Score score;
    public UI UI;

    private Array<GameObject> objects;

    public static final float TORQUE = 6f;
    private static final float LINEAR_DAMPING = 1.85f;
    private static final float ANGULAR_DAMPLING = 2f;
    /**
     * Force at which to bounce player in the opposite direction, if he exceeds set world bounds
     */
    private static final float BOUNDS_REPEL_FRC = 2f;

    public Wall rightWall;
    public Wall upWall;
    public Wall downWall;
    public Wall leftWall;
    private Array<Wall> walls;

    public GameWorld(com.mx.tictactoe.DropGame game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        world = new World(new Vector2(Config.GRAVITY_X, Config.GRAVITY_Y), true);
        rainSpawner = new RainSpawner();
        init();

        // todo default player implementation
//        createPlayer(player)
    }

    private void createBody(BodyDef bodyDef) {
        world.createBody(bodyDef);
    }

    public void update() {
        UI.update();
        world.step(Config.TIME_STEP, Config.VELOCITY_ITERATIONS, Config.POSITION_ITERATIONS);
        player.update();
        player.getBody().setLinearDamping(LINEAR_DAMPING);
        player.getBody().setAngularDamping(ANGULAR_DAMPLING);

        for (GameObject object : objects) {
            object.update();
        }

        world.createBody(player.getBodyDef());

        if (Config.RAINDROPS_SPAWN) {
            for (Iterator<Raindrop> iter = rainSpawner.raindrops.iterator(); iter.hasNext(); ) {
                Raindrop raindrop = iter.next();

                raindrop.setY(raindrop.getY() - raindrop.getEntitySpeed() * Gdx.graphics.getDeltaTime());
                // remove raindrop if it falls off screen
                if (raindrop.getY() + 64 < 0) {
                    iter.remove();
                }

                // remove raindrop if bucket collected it
                if (player.overlaps(raindrop)) {
                    iter.remove();
                    game.assets.DROP_SOUND.play();
                    score.addScore();
                }

                for (Wall wall : walls) {
                    if (player.overlaps(wall) && player.getSpeed() > 1.5f) {
                        System.out.println("banged a wall!");
                        player.health -= 3;
                    }
                }
            }

            if (TimeUtils.nanoTime() - RainSpawner.timeSinceLastDrop > RainSpawner.TIME_UNTIL_NEXT_SPAWN) {
                spawnRaindrops();
            }
        }
        worldBounds();
    }

    public Array<GameObject> getObjects() {
        return objects;
    }

    /**
     * World constraints
     */
    private void worldBounds() {
        // left bound
        if (player.getSprite().getX() - 5 <= 0) {
            player.applyForce(BOUNDS_REPEL_FRC, 0);
//            player.setLinearDamping(LINEAR_DAMPING);
        }

        // right bound
        if (player.getSprite().getX() >= Gdx.graphics.getWidth() - player.getSprite().getWidth() / 2 - 5) {
            player.applyForce(-BOUNDS_REPEL_FRC, 0);
//            player.setLinearDamping(LINEAR_DAMPING);
        }

        // up bound
        if (player.getSprite().getY() + player.getSprite().getHeight() / 2 - 2 >= Gdx.graphics.getHeight()) {
            player.applyForce(0, -BOUNDS_REPEL_FRC);
//            player.setLinearDamping(LINEAR_DAMPING);
        }

        // down bound
        if (player.getSprite().getY() - 2 <= 0) {
//            player.applyForce(0, BOUNDS_REPEL_FRC);
//            player.setLinearDamping(LINEAR_DAMPING);
        }
    }

    /**
     * Android handle
     */
    public void onShow() {
        spawnRaindrops();
        Assets.RAIN_MUSIC.play();
    }

    public void spawnRaindrops() {
        rainSpawner.spawnRaindrop();
    }

    public void init() {
        objects = new Array<>();
        player = new Player(Assets.PLAYER_TEXTURE);
        score = new Score();
        initBodies();
        initWalls();
        objects.add(player);

        Assets.RAIN_MUSIC.setLooping(true);
        Assets.RAIN_MUSIC.setVolume(Config.RAIN_VOLUME);

        Stage stage = new Stage(gameScreen.viewport);
        Gdx.input.setInputProcessor(stage);
        UI = new UI(this, stage, "skin/sgx.json", "skin/sgx.atlas");

        initObjects();
        score.resetScore();
    }

    // BodyDef, FixtureDef here?
    private void initBodies() {
    }

    private void initObjects() {
        for (GameObject object : objects) {
            object.init(world);
        }
    }

    public void initWalls() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float dstFromScreen = 2f;
        float wallWidth = 2f;

        rightWall = new Wall(wallWidth, height, width - dstFromScreen, height);
        upWall = new Wall(width, wallWidth, width, height - dstFromScreen);
        leftWall = new Wall(wallWidth, height, dstFromScreen, height);
        downWall = new Wall(width, wallWidth, width, dstFromScreen);
        objects.add(rightWall);
        objects.add(upWall);
        objects.add(leftWall);
        objects.add(downWall);

        walls = new Array<>();
        walls.add(rightWall);
        walls.add(upWall);
        walls.add(leftWall);
        walls.add(downWall);
    }

    public DropGame getGame() {
        return game;
    }

    public World getWorld() {
        return world;
    }

    public Sprite getPlayerSprite() {
        return player.getSprite();
    }

    public Array<Raindrop> getRaindrops() {
        return rainSpawner.raindrops;
    }

    public int getScore() {
        return score.getScore();
    }

    @Override
    public void dispose() {
        world.dispose();
        player.dispose();
        UI.dispose();
        gameScreen.dispose();
        game.dispose();

        for (GameObject object : objects) {
            System.out.println(object.getClass().getSimpleName() + " disposed.");
            object.dispose();
        }
        System.out.println(this.getClass().getSimpleName() + " disposed.");
    }
}
