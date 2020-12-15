package com.mx.tictactoe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mx.tictactoe.Player;
import com.mx.tictactoe.core.DropGame;
import com.mx.tictactoe.Raindrop;
import com.mx.tictactoe.util.Assets;
import com.mx.tictactoe.util.Config;
import com.mx.tictactoe.core.GameWorld;

public class GameScreen implements Screen {
    final DropGame dropGame;
    private GameWorld gameWorld;
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugMatrix;
    private StringBuilder sb;

    Vector2 mousePos;

    public static final float PIXELS_TO_METERS = 100f;

    public GameScreen(DropGame dropGame) {
        this.dropGame = dropGame;
        gameWorld = new GameWorld(dropGame, this);
        gameWorld.init(Assets.PLAYER_TEXTURE);
        mousePos = new Vector2();
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        sb = new StringBuilder();

        if (Config.RAINDROPS_SPAWN) {
            gameWorld.spawnRaindrops();
        }
    }

    private float elapsed = 0;

    private void updateScene() {
        Player player = gameWorld.player;

        dropGame.batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isTouched()) {
            //
        }

        // handle more than one button pressed at one time
        if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveUp();
            player.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.moveDown();
            player.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveDown();
            player.moveRight();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveUp();
            player.moveRight();
        }

        // handle moving
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.moveDown();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight();
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveUp();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !gameWorld.player.didJump()) {
            player.jump();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Config.PLAYER_DRAW = !Config.PLAYER_DRAW;
        }

        System.out.println(gameWorld.player.energy);

        gameWorld.update();
    }

    private void textDrawHelper(String... lines) {
        // single line into multi line with \n
    }

    private void drawScene() {
        Gdx.gl.glClearColor(0.7f, 0.97f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameWorld.stageAct();

        dropGame.batch.begin();
        dropGame.font.draw(dropGame.batch, sb.append("Energy: ").append(gameWorld.player.energy).toString(), 10f, 45f);


        dropGame.font.draw(dropGame.batch, "Controls:", 20f, Gdx.graphics.getHeight() * 0.9f);
        dropGame.font.draw(dropGame.batch, "WASD", 20f, Gdx.graphics.getHeight() * 0.9f - 20f);
        dropGame.font.draw(dropGame.batch, "Space to jump", 20f, Gdx.graphics.getHeight() * 0.9f - 20f * 2f);
//        dropGame.font.draw(dropGame.batch, "Hold S or W to gain more speed!", 20f, Gdx.graphics.getHeight() * 0.9f - 20f * 3f);

        dropGame.font.draw(dropGame.batch, "Score: " + gameWorld.getScore(), Gdx.graphics.getWidth() / 2f - 40f, Gdx.graphics.getHeight() - 20f);

        if (Config.PLAYER_DRAW) {
            Sprite playerSprite = gameWorld.player.sprite;

            dropGame.batch.draw(playerSprite,
                    playerSprite.getX(),
                    playerSprite.getY(),
                    playerSprite.getOriginX(),
                    playerSprite.getOriginY());
        }

//        game.batch.draw(player.getTexture(), player.getX(), player.getY());

        if (Config.RAINDROPS_SPAWN) {
            // gw
            for (Raindrop raindrop : gameWorld.getRaindrops()) {
                dropGame.batch.draw(Assets.RAINDROP_TEXTURE, raindrop.getX(), raindrop.getY());
            }
        }

        dropGame.batch.draw(gameWorld.textureRegion, 10f, 10f, gameWorld.player.energy / 3f, Assets.ENERGY_BAR.getHeight());

        dropGame.batch.end();
        sb.delete(0, sb.length());
    }

    @Override
    public void render(float delta) {
        camera.update();

        updateScene();
        drawScene();

        debugMatrix = dropGame.batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0);
        debugRenderer.render(gameWorld.getWorld(), debugMatrix);
//        System.out.println("total raindrops: " + raindrops.size);
    }

    @Override
    public void show() {
        gameWorld.onShow();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        debugRenderer.dispose();
    }
}
