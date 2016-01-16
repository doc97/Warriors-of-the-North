package com.tint.wotn.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.tint.wotn.UnitType;
import com.tint.wotn.levels.maps.HexMap;

public class Assets {

	private AssetManager manager;
	
	public void initialize() {
		manager = new AssetManager();
	}
	
	public static void loadTexturesIntoGame() {
		HexMap.loadTextures();
		for(UnitType unitType : UnitType.values())
			unitType.loadTexture();
	}
	
	public void addTextureAtlasesToLoadingQueue() {
		manager.load("textures/packed/WarriorsOfTheNorth.atlas", TextureAtlas.class);
	}
	
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
