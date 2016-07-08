package com.tint.wotn.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.tint.wotn.Core;
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
		
		Core.INSTANCE.world.loadTextures();
	}
	
	/**
	 * Adds all game assets to the manager queue
	 */
	public void loadAssets() {
		addTextureAtlasesToLoadingQueue();
		addTexturesToLoadingQueue();
		addAudioFilesToLoadingQueue();
	}
	
	private void addTextureAtlasesToLoadingQueue() {
		manager.load("textures/packed/WarriorsOfTheNorth.atlas", TextureAtlas.class);
		manager.load("skins/default/uiskin.atlas", TextureAtlas.class);
	}
	
	private void addTexturesToLoadingQueue() {
		manager.load("textures/notpacked/campaign_map.png", Texture.class);
		manager.load("textures/notpacked/dark_edge_campaign_map.png", Texture.class);
	}
	
	private void addAudioFilesToLoadingQueue() {
		manager.load("sounds/btn_click.wav", Sound.class);
		manager.load("sounds/background_music.ogg", Music.class);
	}
	
	/**
	 * Updates asset loading
	 */
	public void updateLoading() {
		manager.update();
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
	
	public Texture getTexture(String filename) {
		return manager.get(filename, Texture.class);
	}
	
	public Sound getSound(String filename) {
		return manager.get(filename, Sound.class);
	}
	
	public Music getMusic(String filename) {
		return manager.get(filename, Music.class);
	}
	
	public void dispose() {
		manager.dispose();
	}
}
