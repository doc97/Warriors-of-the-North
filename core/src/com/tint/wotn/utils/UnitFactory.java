package com.tint.wotn.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.UnitType;
import com.tint.wotn.ecs.EntityData;
import com.tint.wotn.ecs.components.AttackComponent;
import com.tint.wotn.ecs.components.EffectComponent;
import com.tint.wotn.ecs.components.HealthComponent;
import com.tint.wotn.ecs.components.IDComponent;
import com.tint.wotn.ecs.components.MovementComponent;
import com.tint.wotn.ecs.components.OwnerComponent;
import com.tint.wotn.ecs.components.RenderComponent;
import com.tint.wotn.levels.maps.Tile;

public class UnitFactory {
	
	public static Entity createUnitByType(int unitID, int ownerID, UnitType unitType, Vector2 position,
			Vector2 renderOffset, Vector2 renderSize, Color color) {
		Entity entity = null;
		switch(unitType) {
		case RAIDER:
			entity = createUnitWithOwner(unitID, ownerID, position,
					UnitType.RAIDER.movementCost,
					UnitType.RAIDER.movementRange,
					UnitType.RAIDER.hp,
					UnitType.RAIDER.attackDmg,
					UnitType.RAIDER.attackCost,
					renderOffset, renderSize,
					UnitType.RAIDER.texture,
					color);
			break;
		default:
			break;
		}
		return entity;
	}
	
	public static Entity createUnitWithOwner(int unitID, int ownerID, Vector2 tilePosition,
			int movementCost, int movementRange, int hp, int attackDmg, int attackCost,
			Vector2 renderOffset, Vector2 renderSize,
			AtlasRegion texture, Color color) {
		Entity entity = createNeutralUnit(unitID, tilePosition, movementCost, movementRange, hp, attackDmg, attackCost, renderOffset, renderSize, texture, color);
		OwnerComponent owner = new OwnerComponent();
		owner.ownerID = ownerID;
		entity.add(owner);
		return entity;
	}
	
	public static Entity createNeutralUnit(int unitID, Vector2 tilePosition,
			int movementCost, int movementRange, int hp, int attackDmg, int attackCost,
			Vector2 renderOffset, Vector2 renderSize,
			AtlasRegion texture, Color color) {
		Entity entity = new Entity();
		MovementComponent movement = new MovementComponent();
		HealthComponent health = new HealthComponent();
		AttackComponent attack = new AttackComponent();
		RenderComponent render = new RenderComponent();
		EffectComponent effect = new EffectComponent();
		IDComponent id = new IDComponent();
		
		movement.position = new Vector2(tilePosition.x, tilePosition.y);
		movement.cost = movementCost;
		movement.range = movementRange;
		health.health = hp;
		attack.damage = attackDmg;
		attack.cost = attackCost;
		render.offset = new Vector2(-Tile.SPACING / 2, -Tile.SPACING / 2);
		render.size = renderSize;
		render.texture = texture;
		render.tintColor = new Color(color);
		id.id = unitID;
		
		entity.add(movement);
		entity.add(health);
		entity.add(attack);
		entity.add(render);
		entity.add(effect);
		entity.add(id);
		
		return entity;
	}
	
	public static Entity createUnit(EntityData entityData) {
		TextureAtlas atlas = Core.INSTANCE.assets.getTextureAtlas("textures/packed/WarriorsOfTheNorth.atlas");
		float[] colArr = entityData.color;
		Color color = new Color(colArr[0], colArr[1], colArr[2], colArr[3]);
		Entity entity = createNeutralUnit(
				entityData.entityID,
				entityData.position,
				entityData.movementCost,
				entityData.movementRange,
				entityData.health,
				entityData.damage,
				entityData.attackCost,
				entityData.offset,
				entityData.size,
				atlas.findRegion(entityData.textureName),
				color
				);
		
		if (entityData.ownerID != -1) {
			OwnerComponent owner = new OwnerComponent();
			owner.ownerID = entityData.ownerID;
			entity.add(owner);
		}
		return entity;
	}
}
