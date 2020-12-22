package com.mx.tictactoe.util.shop;

import com.badlogic.gdx.utils.Array;
import com.mx.tictactoe.actor.player.Player;

public class Shop {
    private final Player player;
    public EngineUpgrade engineUpgrade;

    private Array<ShopItem> shopItems;

    public static final int MAX_SHOP_ITEMS = 16;

    public static final int ENGINE_UPGRADE_PRICE = 5;

    public Shop(Player player) {
        this.player = player;
        shopItems = new Array<>(false, MAX_SHOP_ITEMS);
        engineUpgrade = new EngineUpgrade(player);
    }

    public Array<ShopItem> getShopItems() {
        return shopItems;
    }
}
