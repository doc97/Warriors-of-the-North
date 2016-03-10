package com.tint.wotn;

import com.badlogic.gdx.Game;
import com.tint.wotn.screens.Screens;

/**
 * A class that is called from the platform launchers and launches the core
 * game
 * @author doc97
 *
 */
public class WarriorsOfTheNorth extends Game {
	
	@Override
	public void create () {
		Core.INSTANCE.initializeAll(this);
		Core.INSTANCE.screenSystem.setScreenToEnter(Screens.LOADING);
		Core.INSTANCE.screenSystem.update();
	}

	@Override
	public void dispose() {
		super.dispose();
		Core.INSTANCE.dispose();
	}
}
