package com.tint.wotn.utils.fsm;

public abstract class StateTransition {

	private State state;
	
	public StateTransition(State state) {
		this.state = state;
	}
	
	public abstract boolean isReady();
	
	public State getState() {
		return state;
	}
}
