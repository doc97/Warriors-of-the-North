package com.tint.wotn.ecs;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.tint.wotn.Core;
import com.tint.wotn.ecs.components.IDComponent;
import com.tint.wotn.ecs.systems.CombatSystem;
import com.tint.wotn.ecs.systems.RenderSystem;

public class EntityComponentSystem {
	public Engine engine;
	
	public void initialize() {
		engine = new Engine();
		
		//Add systems
		engine.addSystem(new CombatSystem());
		engine.addSystem(new RenderSystem());
		
		engine.addEntityListener(new EntityListener() {

			@Override
			public void entityAdded(Entity entity) {
				if(entity.getComponent(IDComponent.class) != null)
					Core.INSTANCE.entityIDSystem.addEntity(entity);
			}

			@Override
			public void entityRemoved(Entity entity) {
				IDComponent idComponent = Mappers.id.get(entity);
				if(idComponent != null)
					Core.INSTANCE.entityIDSystem.removeEntityByID(idComponent.id);
			}
		});
	}
}