package com.mx.tictactoe.util.shop;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.mx.tictactoe.actor.player.Player;
import com.mx.tictactoe.core.GameWorld;

public class EngineUpgrade extends Actor {
    private static final String NAME = "Engine Upgrade";
    private static final String DESCRIPTION = "";
    public static final int DEFAULT_PRICE = 10;
    private final Player player;
    private final Skin skin;

    private final Button upgradeEngineButton;

    private static final int MAX_UPGRADES = 8;
    private static int currentUpgrade = 0;

    private static int price = Shop.ENGINE_UPGRADE_PRICE;

    public Array<Actor> actors;

    private final GameWorld gameWorld;

    public EngineUpgrade(final GameWorld gameWorld, Player player, Skin skin) {
        this.gameWorld = gameWorld;
        this.player = player;
        this.skin = skin;
        actors = new Array<>();
        upgradeEngineButton = new TextButton("Upgrade Engine", skin);
        upgradeEngineButton.setDisabled(true);
        Label engineUpgradeLabel = new Label("Engine Upgrade: " + "5", skin);
        engineUpgradeLabel.setTouchable(Touchable.disabled);

        upgradeEngineButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (gameWorld.getScore() >= 5) {
                    upgrade();
//                    gameWorld.player.setBody(gameWorld.getWorld().createBody(gameWorld.player.getBodyDef()));
                    gameWorld.score.subScore(5);
                }
//                label.setText(textArea.getText());
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        actors.add(engineUpgradeLabel);
        actors.add(upgradeEngineButton);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        upgradeEngineButton.setDisabled(gameWorld.score.getScore() < Shop.ENGINE_UPGRADE_PRICE);
    }

    public void upgrade() {
        if (currentUpgrade++ < MAX_UPGRADES) {
            player.fixtureDef.density -= 10.10f;
            player.fixtureDef.friction -= 10.90f;

            // TODO figure out how to upgrade player body
            player.getBody().createFixture(player.fixtureDef);
        }
    }

    public int getPrice() {
        return price;
    }
}
