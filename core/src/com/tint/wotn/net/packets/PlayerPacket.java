package com.tint.wotn.net.packets;

import com.tint.wotn.net.Player;

/**
 * {@link Packet} that is send by the server to notify the clients of all the
 * other players
 * @author doc97
 *
 */
public class PlayerPacket extends Packet {
	public Player[] players;
}
