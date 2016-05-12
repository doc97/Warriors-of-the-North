package com.tint.wotn.gfx;

import com.badlogic.ashley.core.Entity;

public abstract class Effect {
	
	private EffectListener onCompletionListener;
	private boolean isRunning;

	public void start() {
		isRunning = true;
	}

	public void stop() {
		isRunning = false;
		onCompletionListener.act();
	}

	public abstract void update(Entity entity);
	public abstract void reset();

	public void addOnCompletionListener(EffectListener onCompletionListener) {
		this.onCompletionListener = onCompletionListener;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
}
