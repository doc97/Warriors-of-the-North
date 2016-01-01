package com.tint.wotn.combat;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.ashley.core.Entity;
import com.tint.wotn.Core;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.HealthComponent;

public class CombatSystemTest {

	@Before
	public void setUp() throws Exception {
		Core.INSTANCE.initializeECS();
	}
	
	@After
	public void tearDown() throws Exception {
		Core.INSTANCE.dispose();
	}
	
	@Test
	public void testSimulate() {
		Entity attacker = UnitFactory.createUnit(0, 0, 10, 1);
		Entity defender = UnitFactory.createUnit(1, 0, 10, 2);
		Core.INSTANCE.ecs.engine.addEntity(attacker);
		Core.INSTANCE.ecs.engine.addEntity(defender);
		HealthComponent healthAttacker = Mappers.health.get(attacker);
		HealthComponent healthDefender = Mappers.health.get(defender);
		
		while(healthAttacker.health > 0 && healthDefender.health > 0) {
			Core.INSTANCE.ecs.engine.getSystem(CombatSystem.class).simulate(attacker, defender);
		}
		
		assertTrue(healthDefender.health > healthAttacker.health);
	}
}
