package com.tint.wotn.gfx;

import com.badlogic.ashley.core.Entity;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.RenderComponent;

public class ColorTransitionEffect implements Effect {
	
	public int tickCounter;
	public int beforeDelayTicks;
	public int afterDelayTicks;
	public boolean running;
	public ColourTransition transition;
	
	public ColorTransitionEffect(ColourTransition transition) {
		this.transition = transition;
	}
	
	public boolean update(Entity entity) {
		if(!running) return false;

		if(tickCounter < beforeDelayTicks) {
			tickCounter++;
		} else if(!transition.isFinished()) {
			transition.step();
			RenderComponent renderComponent = Mappers.render.get(entity);
			renderComponent.tintColor.set(transition.getCurrentColor());
		} else if(tickCounter - beforeDelayTicks < afterDelayTicks) {
			tickCounter++;
		} else {
			running = false;
			return true;
		}
		return false;
	}
	
	
}
