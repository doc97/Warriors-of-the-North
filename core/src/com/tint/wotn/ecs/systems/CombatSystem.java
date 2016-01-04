package com.tint.wotn.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.AttackComponent;
import com.tint.wotn.ecs.components.HealthComponent;
import com.tint.wotn.ecs.components.MovementComponent;

public class CombatSystem extends EntitySystem {
	private ImmutableArray<Entity> units;
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		units = engine.getEntitiesFor(Family.all(
				MovementComponent.class,
				HealthComponent.class,
				AttackComponent.class)
				.get());
	}

	
	public void simulate(Entity attacker, Entity defender) {
		if(!units.contains(attacker, true) || !units.contains(defender, true)) return;
		if(!isValidTarget(attacker, defender)) return;
		HealthComponent healthAttacker = Mappers.health.get(attacker);
		HealthComponent healthDefender = Mappers.health.get(defender);
		AttackComponent attackAttacker = Mappers.attack.get(attacker);
		AttackComponent attackDefender = Mappers.attack.get(defender);
		
		healthDefender.health -= attackAttacker.damage;
		
		// Retaliation
		if(healthDefender.health > 0)
			healthAttacker.health -= attackDefender.damage;
	}
	
	private boolean isValidTarget(Entity attacker, Entity defender) {
		return !attacker.equals(defender);
	}
}
