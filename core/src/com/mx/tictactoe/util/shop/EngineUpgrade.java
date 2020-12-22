package com.mx.tictactoe.util.shop;

public class EngineUpgrade extends ShopItem {
    private static final String NAME = "Engine Upgrade";
    private static final String DESCRIPTION = "";
    public static final int DEFAULT_PRICE = 10;

    private static int price = DEFAULT_PRICE;

    public EngineUpgrade() {
        super(NAME, DESCRIPTION, price);
    }


}
