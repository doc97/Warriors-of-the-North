package com.tint.wotn.ecs;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;

public class EntityData implements Serializable {

	private static final long serialVersionUID = -7257149103168557449L;

	public Vector2 position;
	public Vector2 size;
	public Vector2 offset;
	public String atlasName;
	public String textureName;
	public float[] color;
	
	public int entityID;
	public int ownerID;
	public int movementCost;
	public int movementRange;
	public int health;
	public int damage;
	public int attackCost;
}
