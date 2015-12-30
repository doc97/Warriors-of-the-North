package com.tint.wotn;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.tint.wotn.screens.GameScreen;
import com.tint.wotn.screens.LoadingScreen;
import com.tint.wotn.screens.Screens;

public class ScreenManager {
	private Map<Screens, Screen> screens;
	
	public ScreenManager() {
		screens = new HashMap<Screens, Screen>();
	}
	
	public void initialize() {
		screens.put(Screens.LOADING, new LoadingScreen());
		screens.put(Screens.GAME, new GameScreen());
	}
	
	public void enterScreen(Screens scr) {
		Screen s = screens.get(scr);
		if(s != null)
			Core.INSTANCE.wotn.setScreen(s);
		else
			Gdx.app.log("ScreenManager", "No screen with id: " + scr.toString());
	}
	
	public void dispose() {
		for(Screen s : screens.values())
			s.dispose();
	}
}
