package com.tint.wotn.gfx;

import java.util.Random;

import com.badlogic.ashley.core.Entity;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.RenderComponent;

public class ShakeEffect extends Effect {

	private Random rand;
	private final float maxShake;
	private final float decrease;
	private float shake;
	
	public ShakeEffect(float maxVibration, float decrease) {
		this.maxShake = maxVibration;
		this.decrease = decrease;
		rand = new Random();
	}
	
	@Override
	public void update(Entity entity) {
		if (!isRunning()) return;

		float offsetX = maxShake * shake * (2 * rand.nextFloat() - 1);
		float offsetY = maxShake * shake * (2 * rand.nextFloat() - 1);
		shake = Math.max(0, shake - decrease); 
		
		RenderComponent renderComp = Mappers.render.get(entity);
		renderComp.effectOffset.set(offsetX, offsetY);
		
		if (shake == 0) stop();
	}
	
	@Override
	public void reset() {
		shake = 1.0f;
	}
}
