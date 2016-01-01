package com.tint.wotn.combat;

import com.badlogic.ashley.core.Entity;
import com.tint.wotn.ecs.components.AttackComponent;
import com.tint.wotn.ecs.components.HealthComponent;
import com.tint.wotn.ecs.components.PositionComponent;

public class UnitFactory {
	public static Entity createUnit(float x, float y, int hp, int dmg) {
		Entity entity = new Entity();
		PositionComponent position = new PositionComponent();
		HealthComponent health = new HealthComponent();
		AttackComponent attack = new AttackComponent();
		
		position.x = x;
		position.y = y;
		health.health = hp;
		attack.damage = dmg;
		
		entity.add(position);
		entity.add(health);
		entity.add(attack);
		
		return entity;
	}
}
