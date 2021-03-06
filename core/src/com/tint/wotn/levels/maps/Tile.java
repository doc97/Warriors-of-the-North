package com.tint.wotn.levels.maps;

import java.io.Serializable;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.tint.wotn.Core;

/**
 * Represents a hexagonal tile in a {@link HexMap}
 * @author doc97
 *
 */
public enum Tile implements Serializable {
	NULL("empty_tile", false, 0.0f),
	GRASS("grass_tile", true, 1.0f),
	SNOW("snow_tile", true, 1.0f);
	
	public transient static float SIZE = 64.0f;
	public transient static final int SPACING = (int) (SIZE / 64.0f);
	public transient static final float HEIGHT = SIZE * 2.0f;
	public transient static final float WIDTH = (float) (HEIGHT * Math.sqrt(3) / 2.0f);

	private String textureName;
	public AtlasRegion texture;
	public boolean accessible;
	public float speed;
	
	Tile(String textureName, boolean accessible, float speed) {
		this.textureName = textureName;
		this.accessible = accessible;
		this.speed = speed;
	}
	
	public void loadTexture() {
		if(textureName == null) return;
		TextureAtlas atlas = Core.INSTANCE.assets.getTextureAtlas("textures/packed/WarriorsOfTheNorth.atlas");
		texture = atlas.findRegion(textureName);
	}
}
