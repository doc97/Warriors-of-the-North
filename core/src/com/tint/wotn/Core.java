package com.tint.wotn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tint.wotn.control.UserControlSystem;
import com.tint.wotn.ecs.EntityComponentSystem;
import com.tint.wotn.input.InputSystem;
import com.tint.wotn.levels.LevelSystem;
import com.tint.wotn.net.MultiplayerSystem;

public enum Core {
	INSTANCE;
	
	public Game game;
	public GameMode gameMode = GameMode.MULTI_PLAYER;
	public ScreenSystem screenSystem;
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public Camera camera;
	public EntityComponentSystem ecs;
	public LevelSystem levelSystem;
	public InputSystem inputSystem;
	public UserControlSystem userControlSystem;
	public MultiplayerSystem multiplayerSystem;
	public AssetManager assetManager;
	
	public void initializeAll(Game game) {
		setGame(game);
		initializeAssetManager();
		initializeSpriteBatch();
		initializeShapeRenderer();
		initializeCamera(1920, 1080);
		initializeScreenSystem();
		initializeECS();
		initializeInputSystem();
		initializeUserControlSystem();
		initializeMultiplayerSystem();
		
		if(gameMode == GameMode.SINGLE_PLAYER)
			initializeLevelSystem();
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public void initializeAssetManager() {
		assetManager = new AssetManager();
	}
	
	public void initializeSpriteBatch() {
		batch = new SpriteBatch();
	}
	
	public void initializeShapeRenderer() {
		shapeRenderer = new ShapeRenderer();
	}
	
	public void initializeCamera(int width, int height) {
		camera = new Camera(width, height);
		camera.initialize();
	}
	
	public void initializeScreenSystem() {
		screenSystem = new ScreenSystem();
		screenSystem.initialize();
	}
	
	public void initializeECS() {
		ecs = new EntityComponentSystem();
		ecs.initialize();
	}
	
	public void initializeLevelSystem() {
		levelSystem = new LevelSystem();
		levelSystem.initialize();
	}
	
	public void initializeInputSystem() {
		inputSystem = new InputSystem();
		inputSystem.initialize();
	}
	
	public void initializeUserControlSystem() {
		userControlSystem = new UserControlSystem();
	}
	
	public void initializeMultiplayerSystem() {
		multiplayerSystem = new MultiplayerSystem();
	}
	
	public void dispose() {
		if(screenSystem != null)	screenSystem.dispose();
		if(batch != null)			batch.dispose();
		if(shapeRenderer != null)	shapeRenderer.dispose();
		
		game = null;
		screenSystem = null;
		batch = null;
		shapeRenderer = null;
		assetManager = null;
		camera = null;
		ecs = null;
		levelSystem = null;
		inputSystem = null;
	}
}
