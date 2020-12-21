package com.mx.tictactoe.core.ui.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ControlsInfo {
    public Label controls;
    public Label wasd;
    public Label spaceToJump;

    public ControlsInfo(Skin skin) {
        controls = new Label("Controls:", skin);
        wasd = new Label("WASD", skin);
        spaceToJump = new Label("Space to boost", skin);
        controls.setPosition(5f, Gdx.graphics.getHeight() - 50f);
        wasd.setPosition(5f, Gdx.graphics.getHeight() - 67f);
        spaceToJump.setPosition(5f, Gdx.graphics.getHeight() - 84f);
    }
}
