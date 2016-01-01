package com.tint.wotn.levels;

import java.util.ArrayList;
import java.util.List;

import com.tint.wotn.levels.maps.HexMap;
import com.tint.wotn.levels.maps.Tile;

public class LevelSystem {
	private List<Level> levels = new ArrayList<Level>();
	private Level nullLevel;
	private int currentLevelID = -1;
	
	public void initialize() {
		nullLevel = new Level("Null");
		nullLevel.setMap(HexMap.createMap(new Tile[0][0]));

		Level level0 = new Level("Beginning");
			Tile[][] tiles0 = {
				{
					Tile.GRASS, Tile.GRASS, Tile.GRASS, Tile.GRASS
				},
				{
					Tile.GRASS, Tile.NULL, Tile.NULL, Tile.GRASS
				},
				{
					Tile.GRASS, Tile.GRASS, Tile.GRASS, Tile.GRASS
				}
			};
			level0.setMap(HexMap.createMap(tiles0));
		addLevel(level0);
	}
	
	public void enterLevel(int id) {
		if(isValidID(id)) {
			if(isValidID(currentLevelID))
				levels.get(currentLevelID).exit();
			levels.get(id).enter();
			currentLevelID = id;
		}
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
