package com.tint.wotn;

import com.badlogic.gdx.Game;

public class WarriorsOfTheNorth extends Game {
	@Override
	public void create () {
		Core.INSTANCE.initialize(this);
	}

	@Override
	public void dispose() {
		super.dispose();
		Core.INSTANCE.dispose();
	}
}
