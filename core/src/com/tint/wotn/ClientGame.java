package com.tint.wotn;

import com.tint.wotn.net.Player;

public class ClientGame {
	public int playerInTurnID = -1;
	public Player player;
	
	public boolean isPlayersTurn() {
		if(player.id < 0) return false;
		return playerInTurnID == player.id;
	}
}
