package com.tint.wotn.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.tint.wotn.gfx.ColorTransitionEffect;

public class EffectComponent implements Component {
	public ColorTransitionEffect colorTransition;
	
	public void update(Entity entity) {
		colorTransition.update(entity);
	}
}
