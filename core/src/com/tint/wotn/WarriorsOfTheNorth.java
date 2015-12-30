package com.tint.wotn;

import com.badlogic.gdx.Game;
import com.tint.wotn.screens.Screens;

public class WarriorsOfTheNorth extends Game {
	@Override
	public void create () {
		Core.INSTANCE.initialize(this);
		Core.INSTANCE.screenManager.enterScreen(Screens.LOADING);
	}

	@Override
	public void dispose() {
		super.dispose();
		Core.INSTANCE.dispose();
	}
}
