package com.tint.wotn.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tint.wotn.UnitType;

/**
 * Represents a player
 * @author doc97
 *
 */
public class Player {
	private String name;
	private int id;
	private transient List<UnitData> units = new ArrayList<UnitData>();
	private transient Map<UnitType, Integer> loadout = new HashMap<UnitType, Integer>();
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setUnits(List<UnitData> units) {
		if (units == null) return;
		this.units = units;
	}
	
	public void setLoadout(Map<UnitType, Integer> loadout) {
		this.loadout = loadout;
	}
	
	public void addUnit(UnitData unit) {
		units.add(unit);
	}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return id;
	}
	
	public List<UnitData> getUnits() {
		return units;
	}
	
	public Map<UnitType, Integer> getLoadout() {
		return loadout;
	}
}
