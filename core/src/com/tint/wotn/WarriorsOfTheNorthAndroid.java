package com.tint.wotn;

import com.badlogic.gdx.Game;
import com.tint.wotn.screens.Screens;

public class WarriorsOfTheNorthAndroid extends Game {
	
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