package com.tint.wotn.net;

import java.util.HashMap;
import java.util.Map;

import com.tint.wotn.UnitType;

public class Player {
	public String name;
	public int id;
	public transient Map<UnitType, Integer> loadout = new HashMap<UnitType, Integer>();
}
