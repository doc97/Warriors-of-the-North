package com.tint.wotn.actions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.MovementComponent;

public class MoveAction extends Action {
	public int entityID;
	public Vector2 position;
	
	public void act() {
		Entity entity = Core.INSTANCE.entityIDSystem.getEntityByID(entityID);
		MovementComponent movement = Mappers.movement.get(entity);
		movement.position.set(position);
	}
}
