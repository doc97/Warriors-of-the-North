package com.tint.wotn.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.tint.wotn.net.packets.InitializationPacket;
import com.tint.wotn.net.packets.Packet;
import com.tint.wotn.net.packets.TurnPacket;

public class ClientSystem {

	public List<Client> clients = new ArrayList<Client>();
	public static int TIME_OUT_MS = 5000;
	
	public void connect(String hostAddress, int tcpPort) throws IOException {
		final Client client = new Client();
		Packet.register(client.getKryo());
		client.start();
		client.connect(TIME_OUT_MS, hostAddress, tcpPort);
		clients.add(client);
		
		client.addListener(new Listener() {
			@Override
			public void received(Connection connection, Object packet) {
				super.received(connection, packet);
				if(packet instanceof InitializationPacket) {
					System.out.println(((InitializationPacket) packet).text);
					TurnPacket turnPacket = new TurnPacket();
					turnPacket.text = "Thank you";
					client.sendTCP(turnPacket);
				}
			}
		});
	}
}
