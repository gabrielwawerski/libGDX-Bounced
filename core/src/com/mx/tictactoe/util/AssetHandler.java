package com.mx.tictactoe.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;

/**
 * Singleton wrapper for {@link AssetManager}
 */
public final class AssetHandler extends AssetManager {
    private static AssetHandler instance;
    // TODO add asset's name managing
    private ArrayList<?> assets;

    private AssetHandler() {}

    public static AssetHandler getInstance() {
        if (instance == null)
            instance = new AssetHandler();
        return instance;
    }

    /**
     * Stops all code execution to load assets synchronously (on the same thread). This ensures that all assets have been
     * loaded when method finishes.
     */
    public void loadAssets() {
        instance.load("tile.atlas", TextureAtlas.class);
        instance.load("player.png", Texture.class);
        instance.load("enemy.png", Texture.class);
        instance.finishLoading();
    }

    public void loadAssets(boolean synchronously) {
        instance.load("tile.atlas", TextureAtlas.class);
        instance.load("player.png", Texture.class);
        instance.load("enemy.png", Texture.class);

        if (synchronously) {
            instance.finishLoading();
        }
    }
}