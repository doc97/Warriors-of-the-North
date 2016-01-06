package com.tint.wotn.net.packets;

import java.util.HashMap;
import java.util.Map;

import com.tint.wotn.UnitType;

public class LoadoutPacket extends Packet {
	public Map<UnitType, Integer> loadout = new HashMap<UnitType, Integer>();
}
