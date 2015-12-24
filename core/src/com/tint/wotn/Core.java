package com.tint.wotn;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public enum Core {
	INSTANCE;
	
	public WarriorsOfTheNorth wotn;
	public ScreenManager screenManager;
	public SpriteBatch batch;
	public Camera camera;
	
	public void initialize(WarriorsOfTheNorth wotn) {
		this.wotn = wotn;
		batch = new SpriteBatch();
		camera = new Camera(1920, 1080);
		camera.initialize();
		screenManager = new ScreenManager();
		screenManager.initialize();
	}
	
	public void dispose() {
		screenManager.dispose();
		batch.dispose();
	}
}
