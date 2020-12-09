package com.mx.tictactoe.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mx.tictactoe.DropGame;

public class MainMenuScreen implements Screen {
    final DropGame game;

    OrthographicCamera camera;
    public MainMenuScreen(DropGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, com.mx.tictactoe.util.Config.WINDOW_WIDTH, com.mx.tictactoe.util.Config.WINDOW_HEIGHT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Drop! v0.1", 100, 150);
        game.font.draw(game.batch, "Tap to begin", 100, 125);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void show() {

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
    }
}
