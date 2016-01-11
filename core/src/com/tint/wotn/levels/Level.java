package com.tint.wotn.levels;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.UnitType;
import com.tint.wotn.levels.maps.HexMapGenerator;
import com.tint.wotn.levels.maps.MapShape;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.utils.UnitFactory;

public class Level {
	public String name;
	public MapShape mapShape;
	public int mapRadius;
	
	public Level(String name) {
		this.name = name;
	}
	
	public void enter() {
		Core.INSTANCE.gameMode.map = HexMapGenerator.generateMap(mapShape, mapRadius);
		
		Vector2 tilePos1 = new Vector2(5, 5);
		Vector2 tilePos2 = new Vector2(3, 5);
		Vector2 renderOffset = new Vector2(0, 0);
		Vector2 renderSize = new Vector2(Tile.SIZE * 2, Tile.SIZE * 2);
		Texture texture = Core.INSTANCE.assetManager.get("textures/unit_raider.png");
		Color color = Color.WHITE;
		int ownerID = 1;
		int movementRange = 2;
		int hp = 3;
		int dmg = 2;
		Entity entity1 = UnitFactory.createUnitByType(ownerID, UnitType.RAIDER, tilePos1, renderOffset, renderSize, color);
		Entity entity2 = UnitFactory.createUnitWithOwner(ownerID, tilePos2, movementRange, hp, dmg, renderOffset, renderSize, texture, color);
		Core.INSTANCE.ecs.engine.addEntity(entity1);
		Core.INSTANCE.ecs.engine.addEntity(entity2);
	}
	
	public void exit() {
		Core.INSTANCE.ecs.engine.removeAllEntities();
	}
}
