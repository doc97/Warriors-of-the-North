package com.tint.wotn.net;

import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.tint.wotn.Core;
import com.tint.wotn.levels.maps.HexMap;
import com.tint.wotn.levels.maps.HexMapGenerator;
import com.tint.wotn.levels.maps.MapShape;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.net.constants.Request;
import com.tint.wotn.net.constants.Status;
import com.tint.wotn.net.packets.LoadoutPacket;
import com.tint.wotn.net.packets.PlayerPacket;
import com.tint.wotn.net.packets.RequestPacket;
import com.tint.wotn.net.packets.StartGamePacket;
import com.tint.wotn.net.packets.StatusPacket;
import com.tint.wotn.screens.Screens;

public class ClientPacketProcessor {

	public MultiplayerSystem multiplayerSystem;
	
	public ClientPacketProcessor(MultiplayerSystem multiplayerSystem) {
		this.multiplayerSystem = multiplayerSystem;
	}
	
	public void process(Connection connection, Object packet) {
		if(packet instanceof PlayerPacket) {
			processPlayerPacket(connection, packet);
		} else if(packet instanceof StartGamePacket) {
			processStartGamePacket(connection, packet);
		} else if(packet instanceof RequestPacket) {
			processRequestPacket(connection, packet);
		}
	}
	
	private void processPlayerPacket(Connection connection, Object packet) {
		Player[] updatedPlayers = ((PlayerPacket) packet).players;
		multiplayerSystem.players.clear();
		for(Player player : updatedPlayers)
			multiplayerSystem.players.put(player.id, player);
		
		synchronized (multiplayerSystem.client) {
			Core.INSTANCE.screenSystem.screenToEnter = Screens.LOBBY;
		}
	}

	private void processStartGamePacket(Connection connection, Object packet) {
		MapShape shape = ((StartGamePacket) packet).mapShape;
		int radius = ((StartGamePacket) packet).mapRadius;
		List<LoadoutData> loadoutDatas = ((StartGamePacket) packet).loadouts;
		
		// Generate map
		Tile[][] tiles = HexMapGenerator.generate(shape, radius);
		Core.INSTANCE.gameMode.map = HexMap.createMap(tiles);
		
		// Load loadouts
		for(LoadoutData loadoutData : loadoutDatas) {
			multiplayerSystem.players.get(loadoutData.id).loadout = loadoutData.loadout;
		}
		
		// Message server that the game is loaded
		StatusPacket statusPacket = new StatusPacket();
		statusPacket.status = Status.GAME_LOADED;
		multiplayerSystem.client.sendTCP(statusPacket);

		synchronized (multiplayerSystem.client) {
			Core.INSTANCE.screenSystem.screenToEnter = Screens.GAME;
		}
	}
	
	private void processRequestPacket(Connection connection, Object packet) {
		int request = ((RequestPacket) packet).request;
		if(request == Request.LOADOUT_REQUEST) {
			System.out.println("Loadout requested by server");
			LoadoutPacket loadoutPacket = new LoadoutPacket();
			loadoutPacket.loadout = multiplayerSystem.player.loadout;
			System.out.println(multiplayerSystem.client.getRemoteAddressTCP().toString());
			multiplayerSystem.client.sendTCP(loadoutPacket);
		}
	}
}
