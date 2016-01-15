package com.tint.wotn;

import com.badlogic.gdx.graphics.Texture;

public enum UnitType {
	RAIDER		(1, 5, 2, 1, 2, 1, "unit_raider.png"),
	WARRIOR		(2, 7, 3, 1, 1, 1, ""),
	BERSERKER	(3, 7, 3, 1, 1, 1, ""),
	LEADER		(2, 5, 3, 1, 1, 1, ""),
	WARHOUND	(2, 3, 3, 1, 3, 1, "");
	
	public int unitCost;
	public int hp;
	public int attackDmg;
	public int attackCost;
	public int movementCost;
	public int movementRange;
	
	public String textureName;
	public Texture texture;
	
	UnitType(int unitCost, int hp, int attackDmg, int attackCost, int movementRange, int movementCost, String textureName) {
		this.unitCost = unitCost;
		this.hp = hp;
		this.attackDmg = attackDmg;
		this.attackCost = attackCost;
		this.movementRange = movementRange;
		this.movementCost = movementCost;
		this.textureName = textureName;
	}

	public void loadTexture() {
		if(textureName == null || textureName == "") return;
		texture = Core.INSTANCE.assetManager.get("textures/" + textureName);
	}
}
