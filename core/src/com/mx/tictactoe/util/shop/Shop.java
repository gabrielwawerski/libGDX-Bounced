package com.mx.tictactoe.util.shop;

import com.badlogic.gdx.utils.Array;
import com.mx.tictactoe.actor.player.Player;

public class Shop {
    private final Player player;
    private Array<ShopItem> shopItems;

    public static final int MAX_SHOP_ITEMS = 16;

    public Shop(Player player) {
        this.player = player;
        shopItems = new Array<>(false, MAX_SHOP_ITEMS);
    }

    public Array<ShopItem> getShopItems() {
        return shopItems;
    }
}
