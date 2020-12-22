package com.mx.tictactoe.util.shop;

public abstract class ShopItem {
    private String itemName;
    private String itemDescription;
    private int price;

    public ShopItem(String itemName, String itemDescription, int price) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public abstract void upgrade();
}
