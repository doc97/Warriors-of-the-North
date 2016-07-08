package com.tint.wotn.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.EffectComponent;

public class EffectSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;
	
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(EffectComponent.class).get());
	}
	
	public void update() {
		for(Entity e : entities) {
			EffectComponent effect = Mappers.effect.get(e);
			effect.updateAllEffects(e);
		}
	}
}
