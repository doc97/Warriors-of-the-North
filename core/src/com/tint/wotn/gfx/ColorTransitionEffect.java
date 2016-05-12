package com.tint.wotn.gfx;

import com.badlogic.ashley.core.Entity;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.RenderComponent;
import com.tint.wotn.utils.TickTimer;

public class ColorTransitionEffect extends Effect {
	
	private TickTimer delay;
	private ColorTransition transition;
	
	public ColorTransitionEffect(ColorTransition transition, int delayTicks) {
		this.transition = transition;
		delay = new TickTimer(delayTicks);
	}
	
	@Override
	public void start() {
		super.start();
		delay.resume();
	}
	
	@Override
	public void update(Entity entity) {
		if(!isRunning()) return;
		
		if (!delay.hasFinished()) {
			delay.update();
			return;
		}
		
		if (!transition.isFinished()) {
			transition.step();
			RenderComponent renderComponent = Mappers.render.get(entity);
			renderComponent.tintColor.set(transition.getCurrentColor());
			return;
		}
	}
	
	@Override
	public void reset() {
		transition.reset();
		delay.reset();
	}
}
