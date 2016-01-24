package com.tint.wotn;

import com.badlogic.gdx.Game;
import com.tint.wotn.screens.Screens;

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
