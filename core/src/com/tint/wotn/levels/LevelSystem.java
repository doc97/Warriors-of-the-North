package com.tint.wotn.levels;

import java.util.ArrayList;
import java.util.List;

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

/**
 * A system that handles level additions and level changes
 * @author doc97
 * @see Level
 */
public class LevelSystem {
	private List<Level> levels = new ArrayList<Level>();
	private Level nullLevel;
	private int currentLevelID = -1;
	
	/**
	 * Loads levels
	 */
	public void initialize() {
		nullLevel = new Level("Null") {
			@Override
			public void load() {
				
			}
			@Override
			public void enter() {
				
			}
		};

		Level level0 = new Level("Beginning") {
			@Override
			public void load() {
				Core.INSTANCE.game.setMap(HexMapGenerator.generateMap(mapShape, mapRadius));

				TextureAtlas atlas = Core.INSTANCE.assets.getTextureAtlas("textures/packed/WarriorsOfTheNorth.atlas");

				Vector2 tilePos1 = new Vector2(5, 5);
				Vector2 tilePos2 = new Vector2(3, 5);
				Vector2 tilePos3 = new Vector2(4, 5);
				//Vector2 tilePos4 = new Vector2(4, 4);
				Vector2 renderOffset = new Vector2(0, 0);
				Vector2 renderSize = new Vector2(Tile.SIZE * 2, Tile.SIZE * 2);
				AtlasRegion texture = atlas.findRegion("unit_raider");
				Color ownColor = new Color(Color.BLUE);
				Color neutralColor = new Color(Color.BROWN);
				int ownerID = 1;
				int movementCost = 1;
				int movementRange = 2;
				int hp = 3;
				int dmg = 2;
				int attackCost = 1;
				Entity entity1 = UnitFactory.createUnitByType(0, ownerID, UnitType.RAIDER, tilePos1, renderOffset, renderSize, ownColor);
				Entity entity2 = UnitFactory.createNeutralUnit(1, tilePos2, movementCost, movementRange, hp, dmg, attackCost, renderOffset, renderSize, texture, neutralColor);
				Entity entity3 = UnitFactory.createNeutralUnit(2, tilePos3, movementCost, movementRange, hp, dmg, attackCost, renderOffset, renderSize, texture, neutralColor);
				Core.INSTANCE.ecs.engine.addEntity(entity1);
				Core.INSTANCE.ecs.engine.addEntity(entity2);
				Core.INSTANCE.ecs.engine.addEntity(entity3);
			}
		};
		level0.mapShape = MapShape.HEXAGON;
		level0.mapRadius = 4;
		addLevel(level0);
	}
	
	/**
	 * Sets the current level, remember to exit the previous level
	 * @param currentLevelID
	 */
	public void setCurrentLevel(int currentLevelID) {
		this.currentLevelID = currentLevelID;
	}
	
	/**
	 * Enters the current level
	 * @param id The ID of the new level
	 */
	public void enterCurrentLevel() {
		getCurrentLevel().load();
		getCurrentLevel().enter();
	}
	
	/**
	 * Exists the current level
	 */
	public void exitCurrentLevel() {
		getCurrentLevel().exit();
		currentLevelID = -1;
	}
	
	private void addLevel(Level level) {
		levels.add(level);
	}
	
	public boolean isValidID(int id) {
		return id >= 0 && id < levels.size();
	}
	
	public int getCurrentLevelID() {
		return currentLevelID;
	}
	
	public Level getCurrentLevel() {
		if(isValidID(currentLevelID)) return levels.get(currentLevelID);
		return nullLevel;
	}
}
