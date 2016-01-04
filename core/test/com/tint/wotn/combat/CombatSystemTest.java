package com.tint.wotn.combat;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.HealthComponent;
import com.tint.wotn.ecs.systems.CombatSystem;

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
		Entity attacker = UnitFactory.createUnit(new Vector2(0, 0), 1, 10, 1, null, null, null, null);
		Entity defender = UnitFactory.createUnit(new Vector2(1, 0), 1, 10, 2, null, null, null, null);
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
