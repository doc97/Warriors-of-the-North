package com.tint.wotn.net.packets;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.esotericsoftware.kryo.Kryo;
import com.tint.wotn.UnitType;
import com.tint.wotn.actions.AttackAction;
import com.tint.wotn.actions.MoveAction;
import com.tint.wotn.levels.maps.MapShape;
import com.tint.wotn.net.LoadoutData;
import com.tint.wotn.net.Player;
import com.tint.wotn.net.UnitData;

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
		kryo.register(ActionPacket.class);
		
		// LibGDX
		kryo.register(Vector2.class);
		
		// Own
		kryo.register(LoadoutData.class);
		kryo.register(UnitData.class);
		kryo.register(MapShape.class);
		kryo.register(Player.class);
		kryo.register(Player[].class);
		kryo.register(UnitType.class);
		kryo.register(Action.class);
		kryo.register(MoveAction.class);
		kryo.register(AttackAction.class);
		
		// Java
		kryo.register(Integer.class);
		kryo.register(ArrayList.class);
		kryo.register(HashMap.class);
	}
}
