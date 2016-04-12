package com.tint.wotn;

import com.tint.wotn.levels.maps.HexMap;
import com.tint.wotn.levels.maps.HexMapGenerator;
import com.tint.wotn.net.Player;

/**
 * Handles the game on the client in all game modes
 * @author doc97
 * @see GameMode
 */
public class ClientGame {
	public int playerInTurnID = -1;
	public Player player;
	public HexMap map = HexMapGenerator.generateMap(null, 0);
	
	/**
	 * Resets turn id for starting new game
	 */
	public void startSingleplayerGame() {
		player = new Player();
		player.id = 1;
		playerInTurnID = 1;
	}
	
	/**
	 * Reset players action points at the start of their turn
	 */
	public void startTurn() {
		Core.INSTANCE.actionSystem.resetActionPoints();
	}
	
	public void exitBattle() {
		Core.INSTANCE.levelSystem.exitLevel();
		Core.INSTANCE.actionSystem.initialize();
		Core.INSTANCE.entityIDSystem.reset();
	}
	
	public boolean isPlayersTurn() {
		if(player.id < 0) return false;
		return playerInTurnID == player.id;
	}
}
