package com.tint.wotn.net.packets;

import java.util.List;

import com.tint.wotn.levels.maps.MapShape;
import com.tint.wotn.net.UnitData;

public class StartGamePacket extends Packet {
	public MapShape mapShape;
	public int mapRadius;
	public List<UnitData> unitDatas;
}
