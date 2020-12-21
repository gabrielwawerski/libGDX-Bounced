package com.mx.tictactoe.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mx.tictactoe.core.util.Config;
import com.mx.tictactoe.DropGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Config.WINDOW_TITLE;
		config.width = Config.WINDOW_WIDTH;
		config.height = Config.WINDOW_HEIGHT;
		config.fullscreen = Config.FULLSCREEN;
		// TODO fix raindrops moving when game in background
		config.backgroundFPS = Config.PAUSE_WHEN_BACKGROUND;
		config.resizable = Config.WINDOW_RESIZABLE;
		config.undecorated = Config.WINDOW_UNDECORATED;
		config.vSyncEnabled = Config.V_SYNC_ENABLED;

		new LwjglApplication(new DropGame(), config);
	}
}
