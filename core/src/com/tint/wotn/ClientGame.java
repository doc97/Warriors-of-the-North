package com.tint.wotn;

import com.tint.wotn.net.Player;

public class ClientGame {
	public int playerInTurnID = -1;
	public Player player;
	
	public void startSingleplayerGame() {
		player = new Player();
		player.id = 1;
		playerInTurnID = 1;
	}
	
	public boolean isPlayersTurn() {
		if(player.id < 0) return false;
		return playerInTurnID == player.id;
	}
}
