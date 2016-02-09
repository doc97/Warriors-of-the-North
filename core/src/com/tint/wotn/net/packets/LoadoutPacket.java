package com.tint.wotn.net.packets;

import java.util.Map;

import com.tint.wotn.UnitType;

/**
 * {@link Packet} for transmitting loadout configurations
 * @author doc97
 *
 */
public class LoadoutPacket extends Packet {
	public Map<UnitType, Integer> loadout;
}
