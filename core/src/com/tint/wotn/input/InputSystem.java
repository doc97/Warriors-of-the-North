package com.tint.wotn.input;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

/**
 * Wrapper for {@link InputMultiplexer} that extends the use of the Observer
 * pattern. You register a listener using {@link Inputs}.
 * <br>
 * Example, adding a game input listener:
 * <pre>
 * register(Inputs.Game, new GameInput());
 * </pre>
 * @author doc97
 *
 */
public class InputSystem {
	private Map<Inputs, InputProcessor> inputs;
	private InputMultiplexer inputMultiplexer;
	
	public InputSystem() {
		inputs = new HashMap<Inputs, InputProcessor>();
		inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	/**
	 * Every input processor needs to be registered. This happens during
	 * runtime to make it more flexible.
	 * @param key The key to map the input processor to
	 * @param processor The input processor
	 * @param force Determines if it should overwrite the old processor if
	 * there already is a value mapped to the key
	 */
	public void register(Inputs key, InputProcessor processor, boolean force) {
		if(!force && inputs.containsKey(key)) return;
		inputs.put(key, processor);
	}
	
	/**
	 * Unregisters an input processor by it's key
	 * @param key
	 */
	public void unregister(Inputs key) {
		inputs.remove(key);
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
