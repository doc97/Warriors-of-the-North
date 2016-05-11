package com.tint.wotn.gfx;

import com.badlogic.ashley.core.Entity;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.RenderComponent;

public class ColorTransitionEffect implements Effect {
	
	private int tickCounter;
	private int beforeDelayTicks;
	private int afterDelayTicks;
	private boolean isRunning;
	private ColorTransition transition;
	
	public ColorTransitionEffect(ColorTransition transition) {
		this.transition = transition;
	}
	
	public void reset() {
		transition.reset();
	}
	
	@Override
	public void update(Entity entity) {
		if(!isRunning) return;

		if(tickCounter < beforeDelayTicks) {
			tickCounter++;
		} else if(!transition.isFinished()) {
			transition.step();
			RenderComponent renderComponent = Mappers.render.get(entity);
			renderComponent.tintColor.set(transition.getCurrentColor());
		} else if(tickCounter - beforeDelayTicks < afterDelayTicks) {
			tickCounter++;
		} else {
			isRunning = false;
		}
	}
	
	public void start() {
		isRunning = true;
	}
	
	public void stop() {
		isRunning = false;
	}
}
