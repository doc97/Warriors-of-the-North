package com.tint.wotn.actions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.EffectComponent;
import com.tint.wotn.ecs.components.MovementComponent;
import com.tint.wotn.gfx.EffectListener;
import com.tint.wotn.gfx.OffsetEffect;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.utils.CoordinateConversions;

/**
 * Represents a move action and stores data about the action
 * @author doc97
 * @see Action
 * 
 */
public class MoveAction extends Action {
	public int entityID;
	public Vector2 position;
	
	public void act() {
		Entity entity = Core.INSTANCE.entityIDSystem.getEntityByID(entityID);

		MovementComponent movement = Mappers.movement.get(entity);
		Vector2 tileDelta = new Vector2(
				movement.position.x - position.x,
				movement.position.y - position.y
				);
		Vector2 worldDelta = CoordinateConversions.axialToWorld(Tile.SIZE, Tile.SPACING, tileDelta);
		movement.position.set(position);

		final EffectComponent effect = Mappers.effect.get(entity);
		effect.addEffect(OffsetEffect.class, new OffsetEffect(worldDelta, Vector2.Zero, 0.1f));
		effect.addOnCompletionListener(OffsetEffect.class, new EffectListener() {
			@Override
			public void act() {
				effect.removeEffect(OffsetEffect.class);
			}
		});
		effect.resetEffect(OffsetEffect.class);
		effect.startEffect(OffsetEffect.class);
	}
}
