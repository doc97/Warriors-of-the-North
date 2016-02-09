package com.tint.wotn;

import com.badlogic.gdx.Game;
import com.tint.wotn.screens.Screens;

/**
 * A class used to identify that the desktop version has been launched
 * @author Daniel
 *
 */
public class WarriorsOfTheNorthDesktop extends Game {
	
	@Override
	public void create () {
		Core.INSTANCE.initializeAll(this);
		Core.INSTANCE.screenSystem.enterScreen(Screens.LOADING);
	}

	@Override
	public void dispose() {
		super.dispose();
		Core.INSTANCE.dispose();
	}
}
