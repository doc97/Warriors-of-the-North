package com.tint.wotn;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tint.wotn.ecs.EntityComponentSystem;
import com.tint.wotn.levels.LevelSystem;

public enum Core {
	INSTANCE;
	
	public WarriorsOfTheNorth wotn;
	public ScreenSystem screenSystem;
	public SpriteBatch batch;
	public Camera camera;
	public EntityComponentSystem ecs;
	public LevelSystem levelSystem;
	public AssetManager assetManager;
	
	public void initialize(WarriorsOfTheNorth wotn) {
		this.wotn = wotn;
		assetManager = new AssetManager();
		batch = new SpriteBatch();
		camera = new Camera(1920, 1080);
		screenSystem = new ScreenSystem();
		ecs = new EntityComponentSystem();
		levelSystem = new LevelSystem();
		
		camera.initialize();
		screenSystem.initialize();
		ecs.initialize();
		levelSystem.initialize();
	}
	
	public void dispose() {
		screenSystem.dispose();
		batch.dispose();
	}
}
