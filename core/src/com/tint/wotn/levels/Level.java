package com.tint.wotn.levels;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
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
		Core.INSTANCE.game.map = HexMapGenerator.generateMap(mapShape, mapRadius);
		
		TextureAtlas atlas = Core.INSTANCE.assets.getTextureAtlas("textures/packed/WarriorsOfTheNorth.atlas");

		Vector2 tilePos1 = new Vector2(5, 5);
		Vector2 tilePos2 = new Vector2(3, 5);
		Vector2 renderOffset = new Vector2(0, 0);
		Vector2 renderSize = new Vector2(Tile.SIZE * 2, Tile.SIZE * 2);
		AtlasRegion texture = atlas.findRegion("unit_raider");
		Color color = Color.WHITE;
		int ownerID = 1;
		int movementCost = 1;
		int movementRange = 2;
		int hp = 3;
		int dmg = 2;
		int attackCost = 1;
		Entity entity1 = UnitFactory.createUnitByType(0, ownerID, UnitType.RAIDER, tilePos1, renderOffset, renderSize, color);
		Entity entity2 = UnitFactory.createUnitWithOwner(1, ownerID, tilePos2, movementCost, movementRange, hp, dmg, attackCost, renderOffset, renderSize, texture, color);
		Core.INSTANCE.ecs.engine.addEntity(entity1);
		Core.INSTANCE.ecs.engine.addEntity(entity2);
		
		Core.INSTANCE.game.startTurn();
	}
	
	public void exit() {
		Core.INSTANCE.ecs.engine.removeAllEntities();
	}
}
