package com.tint.wotn.levels;

import java.util.ArrayList;
import java.util.List;

import com.tint.wotn.levels.maps.MapShape;

public class LevelSystem {
	private List<Level> levels = new ArrayList<Level>();
	private Level nullLevel;
	private int currentLevelID = -1;
	
	public void initialize() {
		nullLevel = new Level("Null");

		Level level0 = new Level("Beginning");
		level0.mapShape = MapShape.HEXAGON;
		level0.mapRadius = 4;
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
