package com.tint.wotn.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tint.wotn.WarriorsOfTheNorth;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Warriors of the North";
		config.fullscreen = false;
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new WarriorsOfTheNorth(), config);
	}
}
