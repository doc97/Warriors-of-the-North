package com.tint.wotn.net.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.tint.wotn.net.GameConnection;
import com.tint.wotn.net.LoadoutData;
import com.tint.wotn.net.Player;
import com.tint.wotn.net.packets.Packet;
import com.tint.wotn.net.packets.PlayerPacket;
import com.tint.wotn.net.packets.TurnPacket;

public class ServerLauncher {
	
	public Server server;
	public ServerPacketProcessor packetProcessor;
	public int tcpPort = 6666;
	public List<Integer> playersReady = new ArrayList<Integer>();
	public List<LoadoutData> loadoutsReady = new ArrayList<LoadoutData>();
	public int turnID = -1;
	
	public void start() throws IOException {
		packetProcessor = new ServerPacketProcessor(this);
		server = new Server() {
			@Override
			protected Connection newConnection() {
				return new GameConnection();
			}
			
		};
		Packet.register(server.getKryo());
		server.start();
		server.bind(tcpPort);
		
		server.addListener(new Listener() {
			@Override
			public void connected(Connection connection) {
				System.out.println("Client " + connection.getID() + " connected");
			}

			@Override
			public void disconnected(Connection connection) {
				handleDisconnect(connection);
			}

			@Override
			public void received(Connection c, Object packet) {
				packetProcessor.process((GameConnection) c, packet);
			}
		});
	}
	
	public void handleDisconnect(Connection connection) {
		if(playersReady.contains(connection.getID()))
			playersReady.remove(connection.getID());
		
		for(Iterator<LoadoutData> it = loadoutsReady.iterator(); it.hasNext();) {
			LoadoutData loadout = it.next();
			if(loadout.id == connection.getID())
				it.remove();
		}
	}
	
	public void nextTurn() {
		nextConnectionID();
		TurnPacket turnPacket = new TurnPacket();
		turnPacket.turnID = turnID;
		server.sendToAllTCP(turnPacket);
	}
	
	public void nextConnectionID() {
		if(turnID < 0) turnID = 0;
		else if(turnID >= server.getConnections().length - 1) turnID = 0;
		else turnID++;
	}
	
	public void updatePlayerNames() {
		Connection[] connections = server.getConnections();
		List<Player> players = new ArrayList<Player>();
		System.out.println("Number of players: " + connections.length);
		for(int i = connections.length - 1; i >= 0; i--) {
			GameConnection gameConnection = (GameConnection) connections[i];
			Player player = new Player();
			player.id = gameConnection.getID();
			player.name = gameConnection.name;
			players.add(player);
		}
		
		PlayerPacket playerPacket = new PlayerPacket();
		playerPacket.players = (Player[])players.toArray(new Player[players.size()]);
		server.sendToAllTCP(playerPacket);
	}
	
	public void resetPlayersReady() {
		playersReady.clear();

	}
	
	public boolean allPlayersReady() {
		for(Connection c : server.getConnections())
			if(!playersReady.contains(c.getID()))
				return false;
		
		return true;
	}
	
	public boolean allLoadoutsReady() {
		outer:
		for(Connection c : server.getConnections()) {
			for(LoadoutData loadout : loadoutsReady) {
				if(loadout.id == c.getID())
					continue outer;
			}
			return false;
		}
		
		return true;
	}
	
	public void printServerDetails() {
		System.out.println("TCP Port: " + tcpPort);
	}
	
	public static void main(String[] args) {
		System.out.println("Creating server...");
		ServerLauncher serverLauncher = new ServerLauncher();
		serverLauncher.printServerDetails();
		try {
			serverLauncher.start();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to launch server!");
			System.exit(0);
		}
	}
}
