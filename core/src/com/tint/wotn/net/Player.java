package com.tint.wotn.net;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.tint.wotn.UnitType;

/**
 * Represents a player
 * @author doc97
 *
 */
public class Player implements Serializable {

	private static final long serialVersionUID = -8496974432609669478L;
	private String name;
	private int id;
	private transient Map<UnitType, Integer> loadout = new HashMap<UnitType, Integer>();
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setLoadout(Map<UnitType, Integer> loadout) {
		this.loadout = loadout;
	}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return id;
	}
	
	public Map<UnitType, Integer> getLoadout() {
		return loadout;
	}
}
