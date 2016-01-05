package com.tint.wotn.net.packets;

import com.esotericsoftware.kryo.Kryo;

public abstract class Packet {

	public static void register(Kryo kryo) {
		kryo.register(TurnPacket.class);
		kryo.register(InitializationPacket.class);
	}
}
