package com.tint.wotn.actions;

import com.badlogic.ashley.core.Entity;
import com.tint.wotn.Core;
import com.tint.wotn.ecs.systems.CombatSystem;

/**
 * Represents an attack action and stores data about the action
 * @author doc97
 * @see Action
 * 
 */
public class AttackAction extends Action {
	public int attackerID;
	public int defenderID;
	
	public void act() {
		Entity attacker = Core.INSTANCE.entityIDSystem.getEntityByID(attackerID);
		Entity defender = Core.INSTANCE.entityIDSystem.getEntityByID(defenderID);
		Core.INSTANCE.ecs.engine.getSystem(CombatSystem.class).simulate(attacker, defender);
	}
}
