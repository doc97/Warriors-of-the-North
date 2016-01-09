package com.tint.wotn;

import com.badlogic.gdx.graphics.Texture;

public enum UnitType {
	RAIDER(1, 5, 2, 2, "unit_raider.png"),
	WARRIOR(2, 7, 3, 1, ""),
	BERSERKER(3, 7, 3, 1, ""),
	LEADER(2, 5, 3, 1, ""),
	WARHOUND(2, 3, 3, 3, "");
	
	public int cost;
	public int hp;
	public int dmg;
	public int mov;
	
	public String textureName;
	public Texture texture;
	
	UnitType(int cost, int hp, int dmg, int mov, String textureName) {
		this.cost = cost;
		this.hp = hp;
		this.dmg = dmg;
		this.mov = mov;
		this.textureName = textureName;
	}

	public void loadTexture() {
		if(textureName == null || textureName == "") return;
		texture = Core.INSTANCE.assetManager.get("textures/" + textureName);
	}
}
