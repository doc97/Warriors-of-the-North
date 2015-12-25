package com.tint.wotn.ecs;

import com.badlogic.ashley.core.Engine;
import com.tint.wotn.combat.CombatSystem;

public class EntityComponentSystem {
	public Engine engine;
	
	public void initialize() {
		engine = new Engine();
		
		//Add systems
		engine.addSystem(new CombatSystem());
	}
}