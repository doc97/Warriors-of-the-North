package com.tint.wotn.combat;

import javax.swing.plaf.synth.SynthScrollBarUI;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.AttackComponent;
import com.tint.wotn.ecs.components.HealthComponent;
import com.tint.wotn.ecs.components.PositionComponent;

public class CombatSystem extends EntitySystem {
	private ImmutableArray<Entity> units;
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		units = engine.getEntitiesFor(Family.all(
				PositionComponent.class,
				HealthComponent.class,
				AttackComponent.class)
				.get());
	}

	
	public void simulate(Entity e1, Entity e2) {
		if(!units.contains(e1, true) || !units.contains(e1, true)) return;
		HealthComponent health1 = Mappers.health.get(e1);
		HealthComponent health2 = Mappers.health.get(e2);
		AttackComponent attack1 = Mappers.attack.get(e1);
		AttackComponent attack2 = Mappers.attack.get(e2);
		
		health2.health -= attack1.damage;
		
		// Retaliation
		if(health2.health > 0)
			health1.health -= attack2.damage;
		else
			System.out.println("entity 1 killed entity 2.");
		
		System.out.println("Entity 1: " + health1.health);
		System.out.println("Entity 2: " + health2.health);
	}
}
