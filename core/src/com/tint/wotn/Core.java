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
	
	public Game coreGame;
	public GameMode gameMode = GameMode.MULTI_PLAYER;
	public ClientGame game;
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
	
	public void initializeAll(Game coreGame) {
		setCoreGame(coreGame);
		initializeAssetManager();
		initializeSpriteBatch();
		initializeShapeRenderer();
		initializeCamera(1920, 1080);
		initializeScreenSystem();
		initializeECS();
		initializeInputSystem();
		initializeUserControlSystem();
		initializeGame();
	}
	
	public void setCoreGame(Game coreGame) {
		this.coreGame = coreGame;
	}
	
	public void initializeGame() {
		game = new ClientGame();
		if(gameMode == GameMode.SINGLE_PLAYER)
			initializeLevelSystem();
		else if(gameMode == GameMode.MULTI_PLAYER)
			initializeMultiplayerSystem();
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
		
		coreGame = null;
		game = null;
		screenSystem = null;
		batch = null;
		shapeRenderer = null;
		assetManager = null;
		camera = null;
		ecs = null;
		levelSystem = null;
		inputSystem = null;
		multiplayerSystem = null;
		userControlSystem = null;
	}
}
