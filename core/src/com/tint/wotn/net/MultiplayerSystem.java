package com.tint.wotn.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.tint.wotn.Core;
import com.tint.wotn.net.packets.NamePacket;
import com.tint.wotn.net.packets.Packet;

/**
 * A system that handles multiplayer and can connect to a server
 * @author doc97
 * @see ClientPacketProcessor
 */
public class MultiplayerSystem {

	public Client client = new Client();
	public ClientPacketProcessor packetProcessor;
	public Map<Integer, Player> players = new HashMap<Integer, Player>();
	public static int TIME_OUT_MS = 5000;
	
	public void connect(final Player player, String hostAddress, int tcpPort) throws IOException {
		Core.INSTANCE.game.setClientPlayer(player);
		packetProcessor = new ClientPacketProcessor(this);
		Packet.register(client.getKryo());
		
		client.addListener(new Listener() {
			@Override
			public void connected(Connection connection) {
				Core.INSTANCE.game.getPlayer().setID(client.getID());
				NamePacket namePacket = new NamePacket();
				namePacket.name = player.getName();
				client.sendTCP(namePacket);
			}

			@Override
			public void disconnected(Connection connection) {
				System.out.println("Disconnected from server");
			}

			@Override
			public void received(Connection connection, Object packet) {
				packetProcessor.process(connection, packet);
			}
		});

		client.start();
		client.connect(TIME_OUT_MS, hostAddress, tcpPort);
	}
	
	public void disconnect() {
		if (client.isConnected()) {
			client.close();
		}
	}
}
