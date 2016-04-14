package com.tint.wotn.levels;

import com.tint.wotn.Core;
import com.tint.wotn.levels.maps.MapShape;

/**
 * Represents a level
 * @author doc97
 *
 */
public abstract class Level {
	public String name;
	public MapShape mapShape;
	public int mapRadius;
	
	public Level(String name) {
		this.name = name;
	}
	
	public abstract void load();
	
	public void enter() {
		Core.INSTANCE.game.startTurn();
	}
	
	public void exit() {
		Core.INSTANCE.ecs.engine.removeAllEntities();
	}
}
