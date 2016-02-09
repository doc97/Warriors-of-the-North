package com.tint.wotn.net;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player
 * @author doc97
 *
 */
public class Player {
	public String name;
	public int id;
	public transient List<UnitData> units = new ArrayList<UnitData>();
}
