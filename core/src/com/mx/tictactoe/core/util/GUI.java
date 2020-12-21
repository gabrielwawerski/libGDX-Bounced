package com.mx.tictactoe.core.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.mx.tictactoe.core.GameWorld;
import com.mx.tictactoe.actor.actors.EnergyBar;

public class GUI implements Disposable {
    private GameWorld gameWorld;
    private Stage stage;
    private Skin skin;

    public EnergyBar energyBar;

    public GUI(GameWorld gameWorld, Stage stage, String skinFilePath, String textureAtlasPath) {
        this.gameWorld = gameWorld;
        this.stage = stage;
        energyBar = new EnergyBar(10f, 10f, gameWorld.player);
        skin = new Skin(Gdx.files.internal(skinFilePath), new TextureAtlas(Gdx.files.internal(textureAtlasPath)));

        Button button = new Button(skin);

//        ScaleToAction scaleToAction = new ScaleToAction();
//        scaleToAction.setScale(1.65f);
//        energyBar.addAction(scaleToAction);
        stage.addActor(energyBar);
        stage.addActor(button);
    }

    public void update() {
        stage.getViewport().apply();
        stage.act(Gdx.graphics.getDeltaTime());
    }

    public void draw() {
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        energyBar.dispose();
    }
}
