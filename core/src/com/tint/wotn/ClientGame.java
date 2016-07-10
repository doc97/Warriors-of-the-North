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

	private int playerInTurnID = -1;
	private Player player;
	private HexMap map = HexMapGenerator.generateMap(null, 0);
	
	/**
	 * Resets turn id and action points for starting new game
	 */
	public void startBattle() {
		player = new Player();
		player.setID(1);
		playerInTurnID = 1;
		Core.INSTANCE.actionSystem.resetActionPoints();
	}
	
	/**
	 * Reset players action points at the start of their turn
	 */
	public void startTurn() {
		Core.INSTANCE.actionSystem.resetActionPoints();
	}
	
	public void exitBattle(boolean victory) {
		int levelID = Core.INSTANCE.levelSystem.getCurrentLevelID();
		Core.INSTANCE.levelSystem.exitCurrentLevel();
		Core.INSTANCE.actionSystem.initialize();
		Core.INSTANCE.entityIDSystem.reset();
		
		if (Core.INSTANCE.gameMode == GameMode.SINGLEPLAYER && victory) {
			Core.INSTANCE.missionSystem.completeMission(levelID, true);
		}
	}
	
	public void setClientPlayer(Player player) {
		this.player = player;
	}
	
	public void setMap(HexMap map) {
		this.map = map;
	}
	
	public void setPlayerInTurn(int id) {
		playerInTurnID = id;
	}
	
	public boolean isPlayersTurn() {
		if(player.getID() < 0) return false;
		return playerInTurnID == player.getID();
	}
	
	public boolean isInBattle() {
		return Core.INSTANCE.levelSystem.isValidID(Core.INSTANCE.levelSystem.getCurrentLevelID());
	}
	
	public int getPlayerInTurnID() {
		return playerInTurnID;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public HexMap getMap() {
		return map;
	}
}
