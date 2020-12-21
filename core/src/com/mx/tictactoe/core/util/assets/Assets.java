package com.mx.tictactoe.core.util.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public static final Texture ENERGY_BAR = new Texture("energyBarSmall.png");
    public static final Texture HP_BAR = new Texture("hpbar.png");
    public static final Texture RAINDROP_TEXTURE = new Texture("drop.png");
    public static final Texture PLAYER_TEXTURE = new Texture("player_exactSize.png");
    public static final Music RAIN_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
    public final Sound DROP_SOUND = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

    public void dispose() {
        RAINDROP_TEXTURE.dispose();
        RAIN_MUSIC.dispose();
        DROP_SOUND.dispose();
    }
}
