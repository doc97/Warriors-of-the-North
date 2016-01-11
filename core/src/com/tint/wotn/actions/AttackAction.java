package com.tint.wotn.actions;

import com.badlogic.ashley.core.Entity;
import com.tint.wotn.Core;
import com.tint.wotn.ecs.systems.CombatSystem;

public class AttackAction implements Action {
	public Entity attacker;
	public Entity defender;
	
	public void act() {
		Core.INSTANCE.ecs.engine.getSystem(CombatSystem.class).simulate(attacker, defender);
	}
}
