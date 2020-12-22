package com.mx.tictactoe.util.shop;

import com.mx.tictactoe.actor.player.Player;

public class EngineUpgrade extends ShopItem {
    private static final String NAME = "Engine Upgrade";
    private static final String DESCRIPTION = "";
    public static final int DEFAULT_PRICE = 10;
    private final Player player;

    private static final int MAX_UPGRADES = 8;
    private static int currentUpgrade = 0;

    private static int price = Shop.ENGINE_UPGRADE_PRICE;

    public EngineUpgrade(Player player) {
        super(NAME, DESCRIPTION, price);
        this.player = player;
    }

    @Override
    public void upgrade() {
        if (currentUpgrade < MAX_UPGRADES) {
            player.fixtureDef.density -= 0.10f;
            player.fixtureDef.friction -= 0.90f;
            // TODO figure out how to upgrade player body
            player.getBody().createFixture(player.fixtureDef);
            currentUpgrade++;
        }
    }

    public int getPrice() {
        return price;
    }
}
