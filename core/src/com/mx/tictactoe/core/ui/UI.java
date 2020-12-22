package com.mx.tictactoe.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;
import com.mx.tictactoe.actor.actors.HpBar;
import com.mx.tictactoe.core.GameWorld;
import com.mx.tictactoe.actor.actors.EnergyBar;
import com.mx.tictactoe.core.ui.element.ControlsInfo;
import com.mx.tictactoe.util.shop.Shop;

public class UI implements Disposable {
    private GameWorld gameWorld;
    private Stage stage;
    private Skin skin;

    private Table uiTable;
    public EnergyBar energyBar;
    public HpBar hpBar;
    private final Label score;
    private final Label energy;
    private final Label hp;

    private Shop shop;
    private final Button upgradeEngineButton;

    public UI(final GameWorld gameWorld, Stage stage, String skinFilePath, String textureAtlasPath) {
        this.gameWorld = gameWorld;
        this.stage = stage;
        shop = new Shop(gameWorld.player);
        energyBar = new EnergyBar(220f, 25f, gameWorld.player);
        hpBar = new HpBar(10f, 25f, gameWorld.player);
        skin = new Skin(Gdx.files.internal(skinFilePath), new TextureAtlas(Gdx.files.internal(textureAtlasPath)));
        uiTable = new Table(skin);
        uiTable.setPosition(Gdx.graphics.getWidth() - 250f, Gdx.graphics.getHeight() - 50f);

        upgradeEngineButton = new TextButton("Upgrade Engine", skin);
        ControlsInfo controlsInfo = new ControlsInfo(skin);
        final TextArea textArea = new TextArea("Nowy text area", skin);
        final Label label = new Label("nowy textfield", skin);
        hp = new Label("Health: ", skin);
        hp.setPosition(10f, 45f);

        energy = new Label("Energy: ", skin);
        energy.setPosition(220f, 45f);
        upgradeEngineButton.setName("Koszernik");
        upgradeEngineButton.setDisabled(true);

        upgradeEngineButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                label.setText(textArea.getText());
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        score = new Label("Score: ", skin);
        score.setPosition(Gdx.graphics.getWidth() / 2f - 45f, Gdx.graphics.getHeight() - 50f - 10f);
        score.setColor(Color.WHITE);
        score.setText(score.getText() + Integer.toString(gameWorld.getScore()));

        stage.addActor(energy);
        stage.addActor(score);
        stage.addActor(hp);
        uiTable.add(label);
        uiTable.row();
        uiTable.add(textArea);
        uiTable.row();
        uiTable.add(upgradeEngineButton);
//        ScaleToAction scaleToAction = new ScaleToAction();
//        scaleToAction.setScale(1.65f);
//        energyBar.addAction(scaleToAction);
        stage.addActor(energyBar);
        stage.addActor(hpBar);
        stage.addActor(uiTable);
        stage.addActor(controlsInfo.controls);
        stage.addActor(controlsInfo.wasd);
        stage.addActor(controlsInfo.spaceToJump);
    }

    public void update() {
        stage.getViewport().apply();
        stage.act(Gdx.graphics.getDeltaTime());
        score.setText("Score: " + gameWorld.getScore());
        energy.setText("Energy: " + gameWorld.player.energy);
        hp.setText("Health: " + gameWorld.player.health);
        upgradeEngineButton.setDisabled(gameWorld.score.getScore() < Shop.ENGINE_UPGRADE_PRICE);
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
