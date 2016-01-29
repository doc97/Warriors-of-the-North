package com.tint.wotn.missions;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

public class Mission {
	public Vector2 position;
	public String name;
	public String legend;
	public AtlasRegion icon;
	public int ID;
	public int[] unlockIDs;
}
