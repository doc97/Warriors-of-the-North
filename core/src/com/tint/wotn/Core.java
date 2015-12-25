package com.tint.wotn;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tint.wotn.ecs.EntityComponentSystem;

public enum Core {
	INSTANCE;
	
	public WarriorsOfTheNorth wotn;
	public ScreenManager screenManager;
	public SpriteBatch batch;
	public Camera camera;
	public EntityComponentSystem ecs;
	
	public void initialize(WarriorsOfTheNorth wotn) {
		this.wotn = wotn;
		batch = new SpriteBatch();
		camera = new Camera(1920, 1080);
		camera.initialize();
		screenManager = new ScreenManager();
		screenManager.initialize();
		ecs = new EntityComponentSystem();
		ecs.initialize();
	}
	
	public void dispose() {
		screenManager.dispose();
		batch.dispose();
	}
}
