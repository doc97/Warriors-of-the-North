package com.tint.wotn.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.tint.wotn.WarriorsOfTheNorth;

public class DesktopLauncher {
	public static void main (String[] arg) {
		TexturePacker.processIfModified("textures/unpacked", "textures/packed", "WarriorsOfTheNorth");
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Warriors of the North";
		config.fullscreen = false;
		config.width = 940;
		config.height = 540;
		new LwjglApplication(new WarriorsOfTheNorth(), config);
	}
}
