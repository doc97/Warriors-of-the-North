package com.tint.wotn.gfx;

import com.badlogic.ashley.core.Entity;

public abstract class Effect {
	
	private boolean isRunning;

	public void start() {
		isRunning = true;
	}

	public void stop() {
		isRunning = false;
	}

	public abstract void update(Entity entity);
	public abstract void reset();

	public boolean isRunning() {
		return isRunning;
	}
}
