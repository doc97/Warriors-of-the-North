package com.tint.wotn.screens;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.InputProcessor;
import com.tint.wotn.input.GameInput;
import com.tint.wotn.input.Inputs;

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
