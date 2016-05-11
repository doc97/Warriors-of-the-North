package com.tint.wotn.utils;

public class TickTimer {

	private int currTicks;
	private int tickGoal;
	private boolean isRunning;
	
	public TickTimer(int tickGoal) {
		this.tickGoal = tickGoal;
	}
	
	public void reset() {
		currTicks = 0;
	}
	
	public void resume() {
		isRunning = true;
	}
	
	public void pause() {
		isRunning = false;
	}
	
	public void update() {
		if (!isRunning() || hasFinished()) return;
		currTicks++;
	}
	
	public void setGoal(int tickGoal) {
		this.tickGoal = tickGoal;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public boolean hasFinished() {
		return currTicks >= tickGoal;
	}
}
