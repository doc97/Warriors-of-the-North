package com.tint.wotn.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.tint.wotn.net.packets.NamePacket;
import com.tint.wotn.net.packets.Packet;

public class MultiplayerSystem {

	public Client client = new Client();
	public ClientPacketProcessor packetProcessor;
	public Player player;
	public Map<Integer, Player> players = new HashMap<Integer, Player>();
	public static int TIME_OUT_MS = 5000;
	
	public void connect(final Player player, String hostAddress, int tcpPort) throws IOException {
		this.player = player;
		packetProcessor = new ClientPacketProcessor(this);
		Packet.register(client.getKryo());
		
		client.addListener(new Listener() {
			@Override
			public void connected(Connection connection) {
				player.id = client.getID();
				NamePacket namePacket = new NamePacket();
				namePacket.name = player.name;
				client.sendTCP(namePacket);
			}

			@Override
			public void disconnected(Connection connection) {
				Gdx.app.exit();
			}

			@Override
			public void received(Connection connection, Object packet) {
				packetProcessor.process(connection, packet);
			}
		});

		client.start();
		client.connect(TIME_OUT_MS, hostAddress, tcpPort);
	}
}
