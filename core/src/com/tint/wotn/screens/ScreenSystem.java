package com.tint.wotn.screens;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.tint.wotn.Core;

public class ScreenSystem {
	private Map<Screens, Screen> screens;
	public Screens screenToEnter = null;
	
	public ScreenSystem() {
		screens = new HashMap<Screens, Screen>();
	}
	
	public void initialize() {
		screens.put(Screens.LOADING, new LoadingScreen());
		screens.put(Screens.LOBBY, new LobbyScreen());
		screens.put(Screens.GAME, new GameScreen());
	}
	
	public void update() {
		if(screenToEnter != null) {
			enterScreen(screenToEnter);
			screenToEnter = null;
		}
	}
	
	public void enterScreen(Screens scr) {
		Screen s = screens.get(scr);
		if(s != null)
			Core.INSTANCE.coreGame.setScreen(s);
		else
			Gdx.app.log("ScreenManager", "No screen with id: " + scr.toString());
	}
	
	public void dispose() {
		for(Screen s : screens.values())
			s.dispose();
	}
}
