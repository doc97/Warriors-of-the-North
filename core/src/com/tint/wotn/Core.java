package com.tint.wotn;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public enum Core {
	INSTANCE;
	
	public WarriorsOfTheNorth wotn;
	public ScreenManager screenManager;
	public SpriteBatch batch;
	
	public void initialize(WarriorsOfTheNorth wotn) {
		this.wotn = wotn;
		batch = new SpriteBatch();
		screenManager = new ScreenManager();
		screenManager.initialize();
	}
}
