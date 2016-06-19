package com.tint.wotn.utils.fsm;

import java.util.ArrayList;
import java.util.List;

public class FiniteStateMachine {

	private List<State> states = new ArrayList<State>();
	private State currentState;
	private State nextState;
	
	public void addState(State state) {
		states.add(state);
	}
	
	public void setNextState(State state) {
		nextState = state;
	}
	
	public void setCurrentState(State state) {
		currentState = state;
	}
	
	private void transition(State state) {
		if (currentState != null) currentState.exit();
		if (state != null) state.enter();
		currentState = state;
		nextState = null;
	}
	
	public void update() {
		if (currentState != null) currentState.update();
		if (nextState != null) transition(nextState);
	}
	
	public State getCurrentState() {
		return currentState;
	}
}
