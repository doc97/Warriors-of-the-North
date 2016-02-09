package com.tint.wotn.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.ashley.core.Entity;

/**
 * A system that takes care of the entity ID's that is used for multiplayer
 * @author doc97
 *
 */
public class EntityIDSystem {

	private Map<Integer, Entity> entityIDMap = new HashMap<Integer, Entity>();
	private int highestID = -1;
	
	public void addEntity(Entity entity) {
		entityIDMap.put(highestID + 1, entity);
		highestID++;
		System.out.println();
	}
	
	public void removeEntityByID(int id) {
		entityIDMap.remove(id);
	}
	
	public Entity getEntityByID(int id) {
		return entityIDMap.get(id);
	}
	
	public void reset() {
		highestID = -1;
		entityIDMap.clear();
	}
}