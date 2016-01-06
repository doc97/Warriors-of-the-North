package com.tint.wotn.net.packets;

import java.util.ArrayList;
import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.tint.wotn.UnitType;
import com.tint.wotn.levels.maps.MapShape;
import com.tint.wotn.net.LoadoutData;
import com.tint.wotn.net.Player;

public abstract class Packet {

	public static void register(Kryo kryo) {		
		// Packets
		kryo.register(NamePacket.class);
		kryo.register(PlayerPacket.class);
		kryo.register(LoadoutPacket.class);
		kryo.register(TurnPacket.class);
		kryo.register(StatusPacket.class);
		kryo.register(RequestPacket.class);
		kryo.register(StartGamePacket.class);
		
		// Other
		kryo.register(LoadoutData.class);
		kryo.register(MapShape.class);
		kryo.register(Player.class);
		kryo.register(Player[].class);
		kryo.register(UnitType.class);
		kryo.register(Integer.class);
		kryo.register(HashMap.class);
		kryo.register(ArrayList.class);
	}
}
