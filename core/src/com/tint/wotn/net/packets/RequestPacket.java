package com.tint.wotn.net.packets;

import com.tint.wotn.net.constants.Request;

/**
 * {@link Packet} that contains a request, the kind of request is specified by
 * a {@link Request} identifier
 * @author doc97
 *
 */
public class RequestPacket extends Packet {
	public int request;
}
