package com.tint.wotn;

import com.tint.wotn.levels.maps.HexMap;
import com.tint.wotn.levels.maps.HexMapGenerator;
import com.tint.wotn.net.Player;

public class ClientGame {
	public int playerInTurnID = -1;
	public Player player;
	public HexMap map = HexMapGenerator.generateMap(null, 0);
	
	public void startSingleplayerGame() {
		player = new Player();
		player.id = 1;
		playerInTurnID = 1;
	}
	
	public void startTurn() {
		Core.INSTANCE.actionSystem.resetActionPoints();
	}
	
	public boolean isPlayersTurn() {
		if(player.id < 0) return false;
		return playerInTurnID == player.id;
	}
}
