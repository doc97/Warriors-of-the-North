package com.tint.wotn.gfx;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.RenderComponent;

public class OffsetEffect extends Effect {

	private Vector2 offset;
	private Vector2 target;
	private Vector2 offsetDiff;
	private float speed;
	
	public OffsetEffect(Vector2 offset, Vector2 target, float speed) {
		this.offset = offset;
		this.target = target;
		this.speed = speed;
		offsetDiff = new Vector2(target.x - offset.x, target.y - offset.y);
	}
	
	@Override
	public void update(Entity entity) {
		if (!isRunning()) return;
		
		RenderComponent renderComp = Mappers.render.get(entity);
		// Asymptotic average
		offsetDiff.set(target.x - offset.x, target.y - offset.y);
		offset.mulAdd(offsetDiff, speed);
		renderComp.effectOffset.set(offset.x, offset.y);
		
		if (offsetDiff.isZero(0.01f)) stop();
	}

	@Override
	public void reset() {}
}
