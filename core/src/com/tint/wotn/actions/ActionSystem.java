package com.tint.wotn.actions;

import java.util.concurrent.ConcurrentLinkedQueue;

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
	
	public void addAction(Action action) {
		if(actionPoints < action.cost) return;
		actionPoints -= action.cost;
		actions.add(action);
	}
	
	public int getActionPoints() {
		return actionPoints;
	}
	
	public int getMaxActionPoints() {
		return maxActionPoints;
	}
}
