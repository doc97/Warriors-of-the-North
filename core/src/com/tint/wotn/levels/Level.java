package com.tint.wotn.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tint.wotn.Core;
import com.tint.wotn.levels.maps.HexMap;

public class Level {
	public String name;
	public HexMap map;
	
	public Level(String name) {
		this.name = name;
	}
	
	public void render(SpriteBatch batch) {
		map.render(batch);
	}
	
	public void setMap(HexMap map) {
		this.map = map;
	}
	
	public void enter() {
		
	}
	
	public void exit() {
		Core.INSTANCE.ecs.engine.removeAllEntities();
	}
}
