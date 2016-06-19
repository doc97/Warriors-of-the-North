package com.tint.wotn.utils.fsm;

import java.util.ArrayList;
import java.util.List;

public abstract class State {
	
	private FiniteStateMachine fsm;
	private List<StateTransition> transitions = new ArrayList<StateTransition>();
	
	public State(FiniteStateMachine fsm) {
		this.fsm = fsm;
	}
	
	public void addTransition(StateTransition transition) {
		transitions.add(transition);
	}
	
	public void update() {
		for (StateTransition transition : transitions) {
			if (transition.isReady()) {
				fsm.setNextState(transition.getState());
			}
		}
	}
	
	public void enter() {}
	public void exit() {}
}
