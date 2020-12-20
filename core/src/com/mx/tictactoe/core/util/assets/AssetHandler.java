package com.mx.tictactoe.core.util.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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
        instance.load("sgx.json", Skin.class);
        instance.finishLoading();
    }

    public void loadAssets(boolean synchronously) {
        if (synchronously) {
            instance.finishLoading();
        }
    }
}
