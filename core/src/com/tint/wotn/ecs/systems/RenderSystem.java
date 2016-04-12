package com.tint.wotn.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.MovementComponent;
import com.tint.wotn.ecs.components.RenderComponent;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.utils.CoordinateConversions;

/**
 * An {@link EntitySystem} handling rendering components
 * @author doc97
 *
 */
public class RenderSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(RenderComponent.class, MovementComponent.class).get());
	}
	
	public void render(SpriteBatch batch) {
		for(Entity entity : entities) {
			RenderComponent renderComponent = Mappers.render.get(entity);
			MovementComponent movement = Mappers.movement.get(entity);
			
			Vector2 worldPos = CoordinateConversions.axialToWorld(Tile.SIZE, Tile.SPACING, movement.position);
			renderComponent.render(batch, worldPos);
		}
	}

}
