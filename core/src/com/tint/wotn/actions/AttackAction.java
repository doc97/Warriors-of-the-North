package com.tint.wotn.actions;

import com.badlogic.ashley.core.Entity;
import com.tint.wotn.Core;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.EffectComponent;
import com.tint.wotn.ecs.systems.CombatSystem;
import com.tint.wotn.gfx.EffectListener;
import com.tint.wotn.gfx.ShakeEffect;

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
		
		final EffectComponent effect = Mappers.effect.get(defender);
		effect.addEffect(ShakeEffect.class, new ShakeEffect(6, 0.1f));
		effect.addOnCompletionListener(ShakeEffect.class, new EffectListener() {
			@Override
			public void act() {
				effect.removeEffect(ShakeEffect.class);
			}
		});
		effect.resetEffect(ShakeEffect.class);
		effect.startEffect(ShakeEffect.class);

		Core.INSTANCE.ecs.engine.getSystem(CombatSystem.class).simulate(attacker, defender);
	}
}
