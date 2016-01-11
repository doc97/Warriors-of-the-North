package com.tint.wotn.actions;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ActionSystem {
	public ConcurrentLinkedQueue<Action> actions = new ConcurrentLinkedQueue<Action>();
	
	public void initialize() {
		actions.clear();
	}
	
	public void update() {
		if(!actions.isEmpty()) {
			Action action = actions.poll();
			action.act();
		}
	}
}
