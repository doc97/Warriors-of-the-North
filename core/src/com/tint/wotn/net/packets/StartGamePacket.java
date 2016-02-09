package com.tint.wotn.net.packets;

import java.util.List;

import com.tint.wotn.levels.maps.MapShape;
import com.tint.wotn.net.UnitData;

/**
 * {@link Packet} sent by the server to the clients with the necessary data to
 * start a new game
 * @author doc97
 *
 */
public class StartGamePacket extends Packet {
	public MapShape mapShape;
	public int mapRadius;
	public List<UnitData> unitDatas;
}
