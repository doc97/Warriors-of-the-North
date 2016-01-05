package com.tint.wotn.net;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.tint.wotn.net.packets.InitializationPacket;
import com.tint.wotn.net.packets.Packet;
import com.tint.wotn.net.packets.TurnPacket;

public class ServerLauncher {
	
	public static Server server;
	public static int tcpPort = 6666;
	
	public static void start() throws IOException {
		server = new Server();
		Packet.register(server.getKryo());
		server.start();
		server.bind(tcpPort);
		
		server.addListener(new Listener() {
			@Override
			public void connected(Connection connection) {
				super.connected(connection);
				System.out.println("Client connected");
				InitializationPacket initPacket = new InitializationPacket();
				initPacket.text = "Greetings, " + connection.getID() + "!";
				server.sendToTCP(connection.getID(), initPacket);
			}

			@Override
			public void disconnected(Connection connection) {
				super.disconnected(connection);
				System.out.println("Client " + connection.getID() + " disconnected");
			}

			@Override
			public void received(Connection connection, Object packet) {
				super.received(connection, packet);
				if(packet instanceof TurnPacket) {
					System.out.println(((TurnPacket) packet).text);
				}
			}
		});
	}
	
	public static void printServerDetails() {
		System.out.println("TCP Port: " + tcpPort);
	}
	
	public static void main(String[] args) {
		System.out.println("Creating server...");
		printServerDetails();
		try {
			ServerLauncher.start();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to launch server!");
			System.exit(0);
		}
	}
}
