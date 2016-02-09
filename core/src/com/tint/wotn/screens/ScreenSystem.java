package com.tint.wotn.screens;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.tint.wotn.Core;

/**
 * Changes screens using a Finite State Machine
 * @author doc97
 * @see Screen
 */
public class ScreenSystem {
	private Map<Screens, Screen> screens;
	public Screens screenToEnter = null;
	
	public ScreenSystem() {
		screens = new HashMap<Screens, Screen>();
	}
	
	/**
	 * Adds screens to the FSM
	 */
	public void initialize() {
		screens.put(Screens.MAIN_MENU, new MainMenuScreen());
		screens.put(Screens.LOADING, new LoadingScreen());
		screens.put(Screens.LOBBY, new LobbyScreen());
		screens.put(Screens.CAMPAIGN, new CampaignScreen());
		screens.put(Screens.BATTLE, new BattleScreen());
		screens.put(Screens.MULTIPLAYER, new MultiplayerScreen());
	}
	
	/**
	 * Checks if it should change screens. Used when one wants to change screens using a flag.
	 * Either because it is not possible because one is in another thread,
	 * or because the change should not be immediate
	 */
	public void update() {
		if(screenToEnter != null) {
			enterScreen(screenToEnter);
			screenToEnter = null;
		}
	}
	
	/**
	 * Changes screens with immediate effect.
	 * The last screen is exited and the new one entered.
	 * @param screen The enum id, for the screen to which it should change
	 */
	public void enterScreen(Screens screen) {
		Screen s = screens.get(screen);
		if(s != null)
			Core.INSTANCE.coreGame.setScreen(s);
		else
			Gdx.app.log("ScreenManager", "No screen with id: " + screen.toString());
	}
	
	public void dispose() {
		for(Screen s : screens.values())
			s.dispose();
	}
}
