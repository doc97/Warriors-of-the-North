package com.tint.wotn.net;

import java.util.HashMap;
import java.util.Map;

import com.tint.wotn.UnitType;

public class LoadoutData {
	public int id;
	public Map<UnitType, Integer> loadout = new HashMap<UnitType, Integer>();
}
