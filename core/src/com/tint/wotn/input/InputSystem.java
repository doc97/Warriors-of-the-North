package com.tint.wotn.input;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

public class InputSystem {
	private Map<Inputs, InputProcessor> inputs;
	private InputMultiplexer inputMultiplexer;
	
	public InputSystem() {
		inputs = new HashMap<Inputs, InputProcessor>();
		inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	public void register(Inputs key, InputProcessor processor) {
		inputs.put(key, processor);
	}
	
	public void add(Inputs input) {
		inputMultiplexer.addProcessor(getProcessor(input));
	}
	
	public void remove(Inputs input) {
		inputMultiplexer.removeProcessor(getProcessor(input));
	}
	
	public InputProcessor getProcessor(Inputs input) {
		return inputs.get(input);
	}
}
