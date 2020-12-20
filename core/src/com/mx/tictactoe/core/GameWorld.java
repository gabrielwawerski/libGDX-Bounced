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
import com.mx.tictactoe.actor.Player;
import com.mx.tictactoe.core.util.GUI;
import com.mx.tictactoe.core.util.assets.Assets;
import com.mx.tictactoe.core.util.RainDropper;
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
    private final RainDropper rainDropper;

    public Player player;
    private int score;

    public com.mx.tictactoe.core.util.GUI gui;

    private Array<GameObject> objects;

    private BodyDef wallBodydef;

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

    public GameWorld(com.mx.tictactoe.DropGame game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        world = new World(new Vector2(Config.GRAVITY_X, Config.GRAVITY_Y), true);
        rainDropper = new RainDropper();
        init();

        // todo default player implementation
//        createPlayer(player)
    }

    public void update() {
        gui.update();
        world.step(Config.TIME_STEP, Config.VELOCITY_ITERATIONS, Config.POSITION_ITERATIONS);
        player.update();
        player.getBody().setLinearDamping(LINEAR_DAMPING);
        player.getBody().setAngularDamping(ANGULAR_DAMPLING);

        for (GameObject object : objects) {
            object.update();
        }

        if (Config.RAINDROPS_SPAWN) {
            for (Iterator<Raindrop> iter = rainDropper.raindrops.iterator(); iter.hasNext(); ) {
                Raindrop raindrop = iter.next();

                raindrop.setY(raindrop.getY() - raindrop.getEntitySpeed() * Gdx.graphics.getDeltaTime());
                // remove raindrop if it falls off screen
                if (raindrop.getY() + 64 < 0) {
                    iter.remove();
                }

                // remove raindrop if bucket collected it
                if (raindrop.overlaps(player)) {
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
            player.applyForce(0, BOUNDS_REPEL_FRC);
//            player.setLinearDamping(LINEAR_DAMPING);
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

    public void init() {
        objects = new Array<>();
        player = new Player(Assets.PLAYER_TEXTURE);
        initBodies();

        objects.add(player);
        initWalls();

        Assets.RAIN_MUSIC.setLooping(true);
        Assets.RAIN_MUSIC.setVolume(Config.RAIN_VOLUME);

        Stage stage = new Stage(gameScreen.viewport);
        Gdx.input.setInputProcessor(stage);
        gui = new GUI(this, stage, "skin/sgx.json", "skin/sgx.atlas");


        initObjects();
        resetScore();
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
        System.out.println(this.getClass().getSimpleName() + " disposed.");
        world.dispose();
        player.dispose();
        gui.dispose();
        gameScreen.dispose();

        for (GameObject object : objects) {
//            object.dispose();
        }
    }
}
