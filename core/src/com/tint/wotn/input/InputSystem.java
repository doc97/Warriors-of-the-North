package com.tint.wotn.input;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.InputProcessor;

public class InputSystem {
	private Map<Inputs, InputProcessor> inputProcessors;
	
	public InputSystem() {
		inputProcessors = new HashMap<Inputs, InputProcessor>();
	}
	
	public void initialize() {
		inputProcessors.put(Inputs.GAME, new GameInput());
	}
	
	public InputProcessor getProcessor(Inputs input) {
		return inputProcessors.get(input);
	}
}
