package com.tint.wotn.net.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tint.wotn.UnitType;
import com.tint.wotn.levels.maps.MapShape;
import com.tint.wotn.net.GameConnection;
import com.tint.wotn.net.LoadoutData;
import com.tint.wotn.net.constants.Request;
import com.tint.wotn.net.constants.Status;
import com.tint.wotn.net.packets.LoadoutPacket;
import com.tint.wotn.net.packets.NamePacket;
import com.tint.wotn.net.packets.RequestPacket;
import com.tint.wotn.net.packets.StartGamePacket;
import com.tint.wotn.net.packets.StatusPacket;

public class ServerPacketProcessor {
	
	public ServerLauncher serverLauncher;
	
	public ServerPacketProcessor(ServerLauncher serverLauncher) {
		this.serverLauncher = serverLauncher;
	}
	
	public void process(GameConnection connection, Object packet) {
		if(packet instanceof NamePacket) {
			processNamePacket(connection, packet);
		} else if(packet instanceof StatusPacket) {
			processStatusPacket(connection, packet);
		} else if(packet instanceof LoadoutPacket) {
			processLoadoutPacket(connection, packet);
		}
	}
	
	private void processNamePacket(GameConnection connection, Object packet) {
		if(connection.name != null) return;
		String name = ((NamePacket) packet).name;
		if(name == null) return;
		name = name.trim();
		if(name.length() == 0) return;

		connection.name = name;
		serverLauncher.updatePlayerNames();
	}
	
	private void processStatusPacket(GameConnection connection, Object packet) {
		int status = ((StatusPacket) packet).status;
		if(status == Status.CLIENT_READY) {
			serverLauncher.playersReady.add(connection.getID());
		
			if(serverLauncher.allPlayersReady()) {
				RequestPacket requestPacket = new RequestPacket();
				requestPacket.request = Request.LOADOUT_REQUEST;
				serverLauncher.server.sendToAllTCP(requestPacket);
			}
		} else if(status == Status.CLIENT_NOT_READY) {
			serverLauncher.playersReady.add(connection.getID());
		} else if(status == Status.GAME_LOADED) {
			serverLauncher.playersReady.add(connection.getID());
			
			if(serverLauncher.allPlayersReady()) {
				serverLauncher.resetPlayersReady();
				serverLauncher.loadoutsReady.clear();
				System.gc();
			
				serverLauncher.nextTurn();
			}
		} else if(status == Status.TURN_END) {
			serverLauncher.nextTurn();
		}
	}
	
	private void processLoadoutPacket(GameConnection connection, Object packet) {
		storeLoadout(connection, packet);
		
		if(serverLauncher.allLoadoutsReady()) {
			sendStartGamePackets();
			serverLauncher.resetPlayersReady();
		}
	}
	
	private void storeLoadout(GameConnection connection, Object packet) {
		Map<UnitType, Integer> packetLoadout = ((LoadoutPacket) packet).loadout;
		LoadoutData receivedLoadoutData = new LoadoutData();
		receivedLoadoutData.id = connection.getID();
		receivedLoadoutData.loadout = packetLoadout;
		serverLauncher.loadoutsReady.add(receivedLoadoutData);
	}
	
	private void sendStartGamePackets() {
		StartGamePacket startGamePacket = new StartGamePacket();
		startGamePacket.mapShape = MapShape.HEXAGON;
		startGamePacket.mapRadius = 8;
	
		List<LoadoutData> loadoutDatas = new ArrayList<LoadoutData>();
		for(int i = 0; i < serverLauncher.loadoutsReady.size(); i++) {
			LoadoutData loadoutData = serverLauncher.loadoutsReady.get(i);
			loadoutDatas.add(loadoutData);
		}
		startGamePacket.loadouts = loadoutDatas;
		serverLauncher.server.sendToAllTCP(startGamePacket);
	}
}
