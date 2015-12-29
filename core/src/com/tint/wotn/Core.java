package com.tint.wotn;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tint.wotn.ecs.EntityComponentSystem;
import com.tint.wotn.screens.Screens;

public enum Core {
	INSTANCE;
	
	public WarriorsOfTheNorth wotn;
	public ScreenManager screenManager;
	public SpriteBatch batch;
	public Camera camera;
	public EntityComponentSystem ecs;
	public AssetManager assetManager;
	
	public void initialize(WarriorsOfTheNorth wotn) {
		this.wotn = wotn;
		assetManager = new AssetManager();
		batch = new SpriteBatch();
		camera = new Camera(1920, 1080);
		camera.initialize();
		screenManager = new ScreenManager();
		screenManager.initialize();
		ecs = new EntityComponentSystem();
		ecs.initialize();
		
		screenManager.enterScreen(Screens.LOADING);
	}
	
	public void dispose() {
		screenManager.dispose();
		batch.dispose();
	}
}
