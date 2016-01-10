package com.tint.wotn.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.UnitType;
import com.tint.wotn.ecs.components.AttackComponent;
import com.tint.wotn.ecs.components.HealthComponent;
import com.tint.wotn.ecs.components.MovementComponent;
import com.tint.wotn.ecs.components.OwnerComponent;
import com.tint.wotn.ecs.components.RenderComponent;

public class UnitFactory {
	
	public static Entity createUnitByType(int ownerID, UnitType unitType, Vector2 position,
			Vector2 renderOffset, Vector2 renderSize, Color color) {
		Entity entity = null;
		switch(unitType) {
		case RAIDER:
			entity = createUnitWithOwner(ownerID, position,
					UnitType.RAIDER.mov,
					UnitType.RAIDER.hp,
					UnitType.RAIDER.dmg,
					renderOffset, renderSize,
					UnitType.RAIDER.texture,
					color);
			break;
		default:
			break;
		}
		return entity;
	}
	
	public static Entity createUnitWithOwner(int ownerID, Vector2 tilePosition,
			int movementRange, int hp, int dmg,
			Vector2 renderOffset, Vector2 renderSize,
			Texture texture, Color color) {
		Entity entity = createNeutralUnit(tilePosition, movementRange, hp, dmg, renderOffset, renderSize, texture, color);
		OwnerComponent owner = new OwnerComponent();
		owner.ownerID = ownerID;
		entity.add(owner);
		return entity;
	}
	
	public static Entity createNeutralUnit(Vector2 tilePosition,
			int movementRange, int hp, int dmg,
			Vector2 renderOffset, Vector2 renderSize,
			Texture texture, Color color) {
		Entity entity = new Entity();
		MovementComponent movement = new MovementComponent();
		HealthComponent health = new HealthComponent();
		AttackComponent attack = new AttackComponent();
		RenderComponent render = new RenderComponent();
		
		movement.position = new Vector2(tilePosition.x, tilePosition.y);
		movement.range = movementRange;
		health.health = hp;
		attack.damage = dmg;
		render.offset = renderOffset;
		render.size = renderSize;
		render.texture = texture;
		render.tintColor = color;
		
		entity.add(movement);
		entity.add(health);
		entity.add(attack);
		entity.add(render);
		
		return entity;
	}
}
