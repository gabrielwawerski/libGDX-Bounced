package com.mx.tictactoe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.mx.tictactoe.Bucket;
import com.mx.tictactoe.core.DropGame;
import com.mx.tictactoe.Raindrop;
import com.mx.tictactoe.util.Assets;
import com.mx.tictactoe.util.Config;
import com.mx.tictactoe.core.GameWorld;
import com.mx.tictactoe.util.RainDropper;

import static com.mx.tictactoe.Bucket.MAX_JUMP_TIME;

public class GameScreen implements Screen, InputProcessor {
    final DropGame game;

    private OrthographicCamera camera;

    private Music rainMusic;

    private GameWorld gameWorld;

    private float torque = 1f;

    Vector2 mousePos;

    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;

    public static final float PIXELS_TO_METERS = 100f;

    public GameScreen(DropGame game) {
        this.game = game;
        mousePos = new Vector2();

        gameWorld = new GameWorld(game, this);
        Bucket bucket = gameWorld.createBucket(new Texture("bucket.png"));
        gameWorld.init(bucket);

        Gdx.input.setInputProcessor(this);

        debugRenderer = new Box2DDebugRenderer();
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);
        rainMusic.setVolume(0.15f);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (Config.SPAWN_RAINDROPS) {
            gameWorld.spawnRaindrop();
        }
    }

    private float elapsed = 0;

    boolean didJump = false;

    private void updateScene() {
        gameWorld.step();
        Body bucketBody = gameWorld.bucket.getBody();

        bucketBody.applyTorque(torque, true);
        game.batch.setProjectionMatrix(camera.combined);
        gameWorld.getBucketSprite().setRotation((float) Math.toDegrees(bucketBody.getAngle()));
        bucketBody.setLinearDamping(1.5f);
        bucketBody.setAngularDamping(0.5f);

        if (Gdx.input.isTouched()) {
            //
        }

        float twoDForce = 1f;
        float oneDForce = 0.6f;

        if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            bucketBody.applyForceToCenter(new Vector2(-twoDForce, 0), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)) {
            bucketBody.applyForceToCenter(new Vector2(-twoDForce, 0), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            bucketBody.applyForceToCenter(new Vector2(twoDForce, 0), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            bucketBody.applyForceToCenter(new Vector2(twoDForce, 0), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            bucketBody.applyForceToCenter(new Vector2(-oneDForce, 0), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            bucketBody.applyForceToCenter(new Vector2(0.15f, -oneDForce), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            bucketBody.applyForceToCenter(new Vector2(oneDForce, 0), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            bucketBody.applyForceToCenter(new Vector2(0, 0), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !didJump) {
            gameWorld.bucket.jump();
        }

        if (gameWorld.bucket.jumpTime == MAX_JUMP_TIME) {
            didJump = false;
        }

        if (gameWorld.bucket.jumpTime == 0) {
            didJump = true;
        }

        if (gameWorld.bucket.jumpTime < MAX_JUMP_TIME) {
            gameWorld.bucket.jumpTime += Bucket.JUMP_RECOVERY_SPEED;
        }
        System.out.println(gameWorld.bucket.jumpTime);

        float linearDampForce = 2.5f;
        float force = 2f;

        // right bound
        if (gameWorld.getBucketSprite().getX() >= Gdx.graphics.getWidth() - gameWorld.getBucketSprite().getWidth() / 2 - 5) {
//            body.setLinearVelocity(-1.5f, body.getLinearVelocity().y);
            bucketBody.applyForce(new Vector2(-force, 0), bucketBody.getPosition(), true);
            bucketBody.setTransform(bucketBody.getPosition(), 180f);
            bucketBody.setLinearDamping(linearDampForce);
        }

        // left bound
        if (gameWorld.getBucketSprite().getX() - 5 <= 0) {
            bucketBody.applyForce(new Vector2(force, 0), bucketBody.getPosition(), true);
            bucketBody.setLinearDamping(linearDampForce);
        }

        // up bound
        if (gameWorld.getBucketSprite().getY() + gameWorld.getBucketSprite().getHeight() / 2 - 2 >= Gdx.graphics.getHeight()) {
            bucketBody.applyForce(new Vector2(0, -force), bucketBody.getPosition(), true);
            bucketBody.setLinearDamping(linearDampForce);
        }

        // down bound
        if (gameWorld.getBucketSprite().getY() - 2 <= 0) {
            bucketBody.applyForce(new Vector2(0, force), bucketBody.getPosition(), true);
            bucketBody.setLinearDamping(linearDampForce);
        }

        gameWorld.update();

}

        float speed = 0.1f;

    private void drawScene() {
        Gdx.gl.glClearColor(0.7f, 0.97f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "Controls:", 20f, Gdx.graphics.getHeight() * 0.9f);
        game.font.draw(game.batch, "WASD", 20f, Gdx.graphics.getHeight() * 0.9f - 20f);
        game.font.draw(game.batch, "Space to jump", 20f, Gdx.graphics.getHeight() * 0.9f - 20f * 2f);
        game.font.draw(game.batch, "Hold S or W to gain more speed!", 20f, Gdx.graphics.getHeight() * 0.9f - 20f * 3f);

        game.font.draw(game.batch, "Score: " + gameWorld.getScore(), Gdx.graphics.getWidth() / 2f - 40f, Gdx.graphics.getHeight() - 20f);

        if (Config.DRAW_BUCKET) {
            Sprite bucketSprite = gameWorld.getBucketSprite();

            game.batch.draw(bucketSprite,
                    bucketSprite.getX(),
                    bucketSprite.getY(),
                    bucketSprite.getOriginX(),
                    bucketSprite.getOriginY());
        }

//        game.batch.draw(bucket.getTexture(), bucket.getX(), bucket.getY());

        if (Config.SPAWN_RAINDROPS) {
            if (TimeUtils.nanoTime() - RainDropper.timeSinceLastDrop > RainDropper.TIME_UNTIL_NEXT_SPAWN) {
                gameWorld.spawnRaindrop();
            }
            // gw
            for (Raindrop raindrop : gameWorld.getRaindrops()) {
                game.batch.draw(Assets.RAINDROP_TEXTURE, raindrop.getX(), raindrop.getY());
            }
        }

        game.batch.end();
    }

    @Override
    public void render(float delta) {
        camera.update();

        updateScene();
        drawScene();

        debugMatrix = game.batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0);
        debugRenderer.render(gameWorld.getWorld(), debugMatrix);
//        System.out.println("total raindrops: " + raindrops.size);
    }

    @Override
    public void show() {
        rainMusic.play();
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
//        bucket.dispose();
//        raindropTexture.dispose();
//        dropSound.dispose();
        rainMusic.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        // The ESC key toggles the visibility of the sprite allow user to see physics debug info
        if (keycode == Input.Keys.ESCAPE)
            Config.DRAW_BUCKET = !Config.DRAW_BUCKET;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
