package com.mx.tictactoe.util.shop;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.mx.tictactoe.actor.player.Player;
import com.mx.tictactoe.core.GameWorld;

public class Shop extends Actor {
    private final GameWorld gameWorld;
    private final Player player;
    public EngineUpgrade engineUpgrade;

    public static final int MAX_SHOP_ITEMS = 16;
    public static final int ENGINE_UPGRADE_PRICE = 5;
    private final Table upgradeEngineTable;

    public final Array<Actor> actors;

    public Shop(GameWorld gameWorld, Player player, Skin skin) {
        this.gameWorld = gameWorld;
        this.player = player;
        actors = new Array<>();
        engineUpgrade = new EngineUpgrade(gameWorld, player, skin);
        upgradeEngineTable = new Table();

        for (Actor actor : engineUpgrade.actors) {
            upgradeEngineTable.add(actor);
            upgradeEngineTable.row();
        }

        actors.add(upgradeEngineTable);
    }

    public Array<Actor> getActors() {
        return actors;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        engineUpgrade.act(delta);
    }
}
