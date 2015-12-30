package com.tint.wotn.levels.maps;

import com.badlogic.gdx.graphics.Texture;
import com.tint.wotn.Core;

public enum Tile {
	NULL("empty.png", false, 0.0f), GRASS("hexagon.png", true, 1.0f);
	
	public static final float SIZE = 64.0f;
	public static final float HEIGHT = SIZE / 2.0f;
	public static final float WIDTH = (float) (Math.sqrt(3) / 2.0f * HEIGHT);

	private String textureName;
	public Texture texture;
	public boolean accessible;
	public float speed;
	
	Tile(String textureName, boolean accessible, float speed) {
		this.textureName = textureName;
		this.accessible = accessible;
		this.speed = speed;
	}
	
	public void loadTexture() {
		if(textureName == null) return;
		texture = Core.INSTANCE.assetManager.get(textureName);
	}
}
