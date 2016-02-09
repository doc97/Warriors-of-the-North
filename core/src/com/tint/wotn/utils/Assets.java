package com.tint.wotn.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.tint.wotn.UnitType;
import com.tint.wotn.levels.maps.HexMap;

/**
 * A wrapper for the {@link AssetManager}
 * @author doc97
 *
 */
public class Assets {

	private AssetManager manager;
	
	public void initialize() {
		manager = new AssetManager();
	}
	
	/**
	 * Saves textures in memory by calling different parts of the code base.
	 * Should be used after that the textures are loaded
	 */
	public void loadTexturesIntoGame() {
		if(!isDoneLoading()) return;
		HexMap.loadTextures();
		for(UnitType unitType : UnitType.values())
			unitType.loadTexture();
	}
	
	/**
	 * Adds all game assets to the manager queue
	 */
	public void loadAssets() {
		addTextureAtlasesToLoadingQueue();
	}
	
	public void addTextureAtlasesToLoadingQueue() {
		manager.load("textures/packed/WarriorsOfTheNorth.atlas", TextureAtlas.class);
		manager.load("skins/default/uiskin.atlas", TextureAtlas.class);
	}
	
	/**
	 * Updates asset loading
	 */
	public void updateLoading() {
		manager.update();
		System.out.println(manager.getProgress() * 100.0f + "%");
	}
	
	public boolean isDoneLoading() {
		return manager.getProgress() == 1;
	}
	
	public AssetManager getManager() {
		return manager;
	}
	
	public TextureAtlas getTextureAtlas(String filename) {
		return manager.get(filename, TextureAtlas.class);
	}
	
	public void dispose() {
		manager.dispose();
	}
}
