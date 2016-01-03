package com.tint.wotn.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.RenderComponent;

public class RenderSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(RenderComponent.class).get());
	}
	
	public void render(SpriteBatch batch) {
		for(Entity entity : entities) {
			RenderComponent renderComponent = Mappers.render.get(entity);
			renderComponent.render(batch);
		}
	}

}
