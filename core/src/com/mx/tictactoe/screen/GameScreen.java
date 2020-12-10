package com.mx.tictactoe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mx.tictactoe.Bucket;
import com.mx.tictactoe.DropGame;
import com.mx.tictactoe.Raindrop;
import com.mx.tictactoe.util.Config;

import java.util.Iterator;

public class GameScreen implements Screen, InputProcessor {
    final DropGame game;

    private OrthographicCamera camera;

    private Texture raindropTexture;
    private Bucket bucket;
    private Music rainMusic;
    private Sound dropSound;
    private Array<Raindrop> raindrops;
    private long timeSinceLastDrop;
    private long timeToSpawnNextDrop = 400000000;
    private World world;
    private Body body;
    private Sprite bucketSprite;

    private float torque = 1f;
    boolean drawSprite = true;

    long jumpTime = 1000;
    boolean jumped = false;

    Vector2 mousePos;

    void getMousePos() {
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
    }

    Vector3 tmpv3 = new Vector3();

    void moveBodyToMouse(Body body) {
        getMousePos();
        unproject(camera, mousePos);
        float angle = body.getAngle();
        body.setTransform(mousePos.x, mousePos.y, angle);
    }

// or

    Vector2 force = new Vector2();

    void pushBodyTowardsMouse(Body body) {
        getMousePos();
        unproject(camera, mousePos);
        Vector2 bodyPos = body.getPosition();
        float strength = 0.2f;
        force.set(mousePos).sub(bodyPos).nor().scl(strength);
        body.applyForce(force, new Vector2(body.getPosition().x, body.getPosition().y), true);
    }

    void unproject(Camera cam, Vector2 vec) {
        tmpv3.set(vec.x, vec.y, 0f);
        cam.unproject(tmpv3);
        vec.set(tmpv3.x, tmpv3.y);
    }

    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;

    final float PIXELS_TO_METERS = 100f;
    static final long MAX_JUMP_TIME = 550;
    long jump = MAX_JUMP_TIME;

    public GameScreen(DropGame game) {
        raindropTexture = new Texture("drop.png");
        bucket = new Bucket(new Texture("bucket.png"));
        this.game = game;
        mousePos = new Vector2();

        world = new World(new Vector2(0, -3), true);
        bucketSprite = new Sprite(bucket.getTexture());

        BodyDef bucketBodyDef = new BodyDef();
        bucketBodyDef.type = BodyDef.BodyType.DynamicBody;
        bucketBodyDef.position.set(
                (bucketSprite.getX() + bucketSprite.getWidth() / 2) / PIXELS_TO_METERS,
                (bucketSprite.getY() + bucketSprite.getHeight() / 2) / PIXELS_TO_METERS
        );

        body = world.createBody(bucketBodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                bucketSprite.getWidth() / 2 / PIXELS_TO_METERS,
                bucketSprite.getHeight() / 2 / PIXELS_TO_METERS
        );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.35f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.4f;
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        Gdx.input.setInputProcessor(this);

        debugRenderer = new Box2DDebugRenderer();

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        dropSound.setVolume(1, 0.45f);
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);
        rainMusic.setVolume(0.15f);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (Config.SPAWN_RAINDROPS) {
            raindrops = new Array<>();
            spawnRaindrop();
        }

        body.setLinearVelocity(2f, 2f);
    }

    private float elapsed = 0;

    @Override
    public void render(float delta) {
        camera.update();

        updateScene();
        drawScene();

        debugMatrix = game.batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0);
//        debugRenderer.render(world, debugMatrix);
//        System.out.println("total raindrops: " + raindrops.size);
    }

    boolean didJump = false;

    private void updateScene() {
        world.step(1f / 60f, 1, 3);
        body.applyTorque(torque, true);
        game.batch.setProjectionMatrix(camera.combined);
        bucketSprite.setRotation((float) Math.toDegrees(body.getAngle()));

        body.setLinearDamping(1.5f);
        body.setAngularDamping(0.5f);

        if (Gdx.input.isTouched()) {
            moveBodyToMouse(body);
        }

        float twoDForce = 1f;
        float oneDForce = 0.6f;

        if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.applyForceToCenter(new Vector2(-twoDForce, 0), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)) {
            body.applyForceToCenter(new Vector2(-twoDForce, 0), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.applyForceToCenter(new Vector2(twoDForce, 0), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.applyForceToCenter(new Vector2(twoDForce, 0), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.applyForceToCenter(new Vector2(-oneDForce, 0), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            body.applyForceToCenter(new Vector2(0.15f, -oneDForce), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.applyForceToCenter(new Vector2(oneDForce, 0), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.applyForceToCenter(new Vector2(0, 0), true);
        }

        long jumpForceDrain = 15;
        long recoverySpeed = 5;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !didJump) {
            if (jump > 0) {
                body.applyForce(new Vector2(0, 0.55f), body.getPosition(), true);
                jump -= jumpForceDrain;
            }
        }


        if (jump == MAX_JUMP_TIME) {
            didJump = false;
        }

        if (jump == 0) {
            didJump = true;
        }

        if (jump < MAX_JUMP_TIME) {
            jump += recoverySpeed;
        }
        System.out.println(jump);

        // right bound
        if (bucketSprite.getX() >= Gdx.graphics.getWidth() - bucketSprite.getWidth() / 2 - 5) {
//            body.setLinearVelocity(-1.5f, body.getLinearVelocity().y);
            body.applyForce(new Vector2(-2f, 0), body.getPosition(), true);
            body.setTransform(body.getPosition(), 180f);
            body.setLinearDamping(2.5f);
        }

        // left bound
        if (bucketSprite.getX() - 5 <= 0) {
            body.applyForce(new Vector2(2f, 0), body.getPosition(), true);
            body.setLinearDamping(2.5f);
        }

        // up bound
        if (bucketSprite.getY() + bucketSprite.getHeight() / 2 - 2 >= Gdx.graphics.getHeight()) {
            body.applyForce(new Vector2(0, -2f), body.getPosition(), true);
            body.setLinearDamping(2.5f);
        }

        // down bound
        if (bucketSprite.getY() - 2 <= 0) {
            body.applyForce(new Vector2(0, 2f), body.getPosition(), true);
            body.setLinearDamping(2.5f);
        }

        bucketSprite.setPosition(
                (body.getPosition().x * PIXELS_TO_METERS) - bucketSprite.getWidth() / 2,
                (body.getPosition().y * PIXELS_TO_METERS) - bucketSprite.getHeight() / 2
        );

        if (Config.SPAWN_RAINDROPS) {
            if (TimeUtils.nanoTime() - timeSinceLastDrop > timeToSpawnNextDrop) {
                spawnRaindrop();
            }

            for (Iterator<Raindrop> iter = raindrops.iterator(); iter.hasNext(); ) {
                Raindrop raindrop = iter.next();

                raindrop.setY(raindrop.getY() - 200 * Gdx.graphics.getDeltaTime());
                // remove raindrop if falls from screen
                if (raindrop.getY() + 64 < 0) {
                    iter.remove();
                }

                // remove raindrop if bucket collected it
                if (bucketSprite.getBoundingRectangle().overlaps(raindrop.getEntity())) {
                    dropSound.play();
                    iter.remove();
                }
            }
        }
        float speed = 0.1f;
    }

    private void drawScene() {
        Gdx.gl.glClearColor(0.7f, 0.97f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        if (drawSprite) {
            game.batch.draw(bucketSprite,
                    bucketSprite.getX(),
                    bucketSprite.getY(),
                    bucketSprite.getOriginX(),
                    bucketSprite.getOriginY());
        }

//        game.batch.draw(bucket.getTexture(), bucket.getX(), bucket.getY());

        if (Config.SPAWN_RAINDROPS) {
            for (Raindrop raindrop : raindrops) {
                game.batch.draw(raindrop.getTexture(), raindrop.getX(), raindrop.getY());
            }
        }


        game.batch.end();
    }

    private void spawnRaindrop() {
        raindrops.add(new Raindrop(raindropTexture, body.getPosition()));
        timeSinceLastDrop = TimeUtils.nanoTime();
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
        bucket.dispose();
        raindropTexture.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        // The ESC key toggles the visibility of the sprite allow user to see physics debug info
        if (keycode == Input.Keys.ESCAPE)
            drawSprite = !drawSprite;
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
