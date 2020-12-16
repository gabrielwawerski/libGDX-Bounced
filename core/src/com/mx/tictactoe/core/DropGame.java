package com.mx.tictactoe.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mx.tictactoe.screen.MainMenuScreen;
import com.mx.tictactoe.util.AssetHandler;
import com.mx.tictactoe.util.Assets;

public class DropGame extends Game {
    public Batch batch;
    public BitmapFont font;
    public Assets assets;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        assets = new Assets();

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        System.out.println(this.getClass().getSimpleName() + " disposed.");
        batch.dispose();
        font.dispose();
        assets.dispose();
        AssetHandler.getInstance().dispose();
    }
}
