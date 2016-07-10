package com.tint.wotn.actions;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.tint.wotn.Core;
import com.tint.wotn.GameMode;
import com.tint.wotn.net.packets.ActionPacket;

/**
 * A A system that handles actions and action points in the game
 * @author doc97
 * @see Action
 * 
 */
public class ActionSystem {

	private int maxActionPoints;
	private int actionPoints;
	private ConcurrentLinkedQueue<Action> actions = new ConcurrentLinkedQueue<Action>();
	
	public void initialize() {
		actions.clear();
		maxActionPoints = 3;
	}
	
	public void resetActionPoints() {
		actionPoints = maxActionPoints;
	}
	
	public void update() {
		if(!actions.isEmpty()) {
			Action action = actions.poll();
			action.act();
		}
	}
	
	public void setMaxActionPoints(int maxActionPoints) {
		this.maxActionPoints = maxActionPoints;
	}
	
	public void setActionPoints(int actionPoints) {
		if(actionPoints > maxActionPoints) return;
		this.actionPoints = actionPoints;
	}
	
	public void addClientAction(Action action) {
		if(actionPoints < action.cost) return;
		actionPoints -= action.cost;
		actions.add(action);
		
		if(Core.INSTANCE.gameMode == GameMode.MULTIPLAYER) {
			ActionPacket actionPacket = new ActionPacket();
			actionPacket.action = action;
			Core.INSTANCE.multiplayerSystem.client.sendTCP(actionPacket);
		}
	}
	
	public void addActionFromPacket(Action action) {
		actions.add(action);
	}
	
	public int getActionPoints() {
		return actionPoints;
	}
	
	public int getMaxActionPoints() {
		return maxActionPoints;
	}
}
