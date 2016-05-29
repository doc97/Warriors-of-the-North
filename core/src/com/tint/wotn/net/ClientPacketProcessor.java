package com.tint.wotn.net;

import java.util.List;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.tint.wotn.Core;
import com.tint.wotn.levels.maps.HexMapGenerator;
import com.tint.wotn.levels.maps.MapShape;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.net.constants.Request;
import com.tint.wotn.net.constants.Status;
import com.tint.wotn.net.packets.ActionPacket;
import com.tint.wotn.net.packets.LoadoutPacket;
import com.tint.wotn.net.packets.PlayerPacket;
import com.tint.wotn.net.packets.RequestPacket;
import com.tint.wotn.net.packets.StartGamePacket;
import com.tint.wotn.net.packets.StatusPacket;
import com.tint.wotn.net.packets.TurnPacket;
import com.tint.wotn.screens.Screens;
import com.tint.wotn.utils.UnitFactory;

/**
 * Processes incoming packets to the clients
 * @author doc97
 *
 */
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
		} else if(packet instanceof TurnPacket) {
			processTurnPacket(connection, packet);
		} else if(packet instanceof ActionPacket) {
			processActionPacket(connection, packet);
		}
	}
	
	private void processPlayerPacket(Connection connection, Object packet) {
		Player[] updatedPlayers = ((PlayerPacket) packet).players;
		multiplayerSystem.players.clear();
		for(Player player : updatedPlayers)
			multiplayerSystem.players.put(player.getID(), player);
		
		synchronized (multiplayerSystem.client) {
			Core.INSTANCE.screenSystem.setScreenToEnter(Screens.LOBBY);
		}
	}

	private void processStartGamePacket(Connection connection, Object packet) {
		MapShape shape = ((StartGamePacket) packet).mapShape;
		int radius = ((StartGamePacket) packet).mapRadius;
		List<UnitData> unitDatas = ((StartGamePacket) packet).unitDatas;
		
		// Generate map
		Core.INSTANCE.game.setMap(HexMapGenerator.generateMap(shape, radius));
		
		// Load loadouts
		synchronized (multiplayerSystem.client) {
			Vector2 offset = new Vector2(0, 0);
			Vector2 size = new Vector2(Tile.SIZE * 2, Tile.SIZE * 2);
			for(UnitData unitData : unitDatas) {
				Entity e = UnitFactory.createUnitByType(
						unitData.unitID,
						unitData.ownerID,
						unitData.unitType,
						unitData.position,
						offset,
						size,
						Color.WHITE);
				Core.INSTANCE.ecs.engine.addEntity(e);
			}
		}
		
		// Message server that the game is loaded
		StatusPacket statusPacket = new StatusPacket();
		statusPacket.status = Status.GAME_LOADED;
		multiplayerSystem.client.sendTCP(statusPacket);

		synchronized (multiplayerSystem.client) {
			Core.INSTANCE.screenSystem.setScreenToEnter(Screens.BATTLE);
		}
	}
	
	private void processRequestPacket(Connection connection, Object packet) {
		int request = ((RequestPacket) packet).request;
		if(request == Request.LOADOUT_REQUEST) {
			LoadoutPacket loadoutPacket = new LoadoutPacket();

			loadoutPacket.loadout = Core.INSTANCE.game.getPlayer().getLoadout();
			multiplayerSystem.client.sendTCP(loadoutPacket);
		}
	}
	
	private void processTurnPacket(Connection connection, Object packet) {
		Core.INSTANCE.game.setPlayerInTurn(((TurnPacket) packet).turnID);
		if(Core.INSTANCE.game.isPlayersTurn())
			Core.INSTANCE.game.startTurn();
	}
	
	private void processActionPacket(Connection connection, Object packet) {
		Core.INSTANCE.actionSystem.addActionFromPacket(((ActionPacket) packet).action);
	}
}
