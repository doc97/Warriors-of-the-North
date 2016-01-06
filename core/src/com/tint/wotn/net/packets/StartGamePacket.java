package com.tint.wotn.net.packets;

import java.util.ArrayList;
import java.util.List;

import com.tint.wotn.levels.maps.MapShape;
import com.tint.wotn.net.LoadoutData;

public class StartGamePacket extends Packet {
	public MapShape mapShape;
	public int mapRadius;
	public List<LoadoutData> loadouts = new ArrayList<LoadoutData>();
}
