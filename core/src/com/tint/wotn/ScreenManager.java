package com.tint.wotn;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Screen;
import com.tint.wotn.screens.GameScreen;
import com.tint.wotn.screens.Screens;

public class ScreenManager {
	private Map<Screens, Screen> screens;
	
	public void initialize() {
		screens = new HashMap<Screens, Screen>();
		screens.put(Screens.GAME, new GameScreen());
		
		enterScreen(Screens.GAME);
	}
	
	public void enterScreen(Screens scr) {
		Screen s = screens.get(scr);
		if(s != null)
			Core.INSTANCE.wotn.setScreen(s);
		else
			System.out.println("No screen with id: " + scr.toString());
	}
	
	public void dispose() {
		for(Screen s : screens.values())
			s.dispose();
	}
}
