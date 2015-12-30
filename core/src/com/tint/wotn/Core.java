package com.tint.wotn;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tint.wotn.ecs.EntityComponentSystem;
import com.tint.wotn.levels.LevelSystem;
import com.tint.wotn.screens.InputSystem;

public enum Core {
	INSTANCE;
	
	public WarriorsOfTheNorth wotn;
	public ScreenSystem screenSystem;
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public Camera camera;
	public EntityComponentSystem ecs;
	public LevelSystem levelSystem;
	public InputSystem inputSystem;
	public AssetManager assetManager;
	
	public void initialize(WarriorsOfTheNorth wotn) {
		this.wotn = wotn;
		assetManager = new AssetManager();
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		camera = new Camera(1920, 1080);
		screenSystem = new ScreenSystem();
		ecs = new EntityComponentSystem();
		levelSystem = new LevelSystem();
		inputSystem = new InputSystem();
		
		camera.initialize();
		screenSystem.initialize();
		ecs.initialize();
		levelSystem.initialize();
		inputSystem.initialize();
	}
	
	public void dispose() {
		screenSystem.dispose();
		batch.dispose();
		shapeRenderer.dispose();
	}
}
