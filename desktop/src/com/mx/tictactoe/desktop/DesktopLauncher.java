package com.mx.tictactoe.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mx.tictactoe.core.util.Config;
import com.mx.tictactoe.DropGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Drop Game";
		config.width = Config.WINDOW_WIDTH;
		config.height = Config.WINDOW_HEIGHT;
		new LwjglApplication(new DropGame(), config);
	}
}
