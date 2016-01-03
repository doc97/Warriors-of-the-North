package com.tint.wotn.combat;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.ecs.components.AttackComponent;
import com.tint.wotn.ecs.components.HealthComponent;
import com.tint.wotn.ecs.components.PositionComponent;
import com.tint.wotn.ecs.components.RenderComponent;

public class UnitFactory {
	public static Entity createUnit(Vector2 tilePosition, int hp, int dmg,
			Vector2 renderPosition, Vector2 renderSize, Texture texture, Color color) {
		Entity entity = new Entity();
		PositionComponent position = new PositionComponent();
		HealthComponent health = new HealthComponent();
		AttackComponent attack = new AttackComponent();
		RenderComponent render = new RenderComponent();
		
		position.x = tilePosition.x;
		position.y = tilePosition.y;
		health.health = hp;
		attack.damage = dmg;
		render.position = renderPosition;
		render.size = renderSize;
		render.texture = texture;
		render.tintColor = color;
		
		entity.add(position);
		entity.add(health);
		entity.add(attack);
		entity.add(render);
		
		return entity;
	}
}
