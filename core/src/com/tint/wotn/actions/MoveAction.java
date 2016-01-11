package com.tint.wotn.actions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.MovementComponent;

public class MoveAction implements Action {
	public Entity entity;
	public Vector2 position;
	
	public void act() {
		MovementComponent movement = Mappers.movement.get(entity);
		movement.position.set(position);
	}
}
