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
	
	public void addEntity(Entity entity, int id) {
		entityIDMap.put(id, entity);
	}
	
	public void removeEntityByID(int id) {
		entityIDMap.remove(id);
	}
	
	public Entity getEntityByID(int id) {
		return entityIDMap.get(id);
	}
	
	public void reset() {
		entityIDMap.clear();
	}
}