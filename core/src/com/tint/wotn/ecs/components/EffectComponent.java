package com.tint.wotn.ecs.components;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.tint.wotn.gfx.Effect;

public class EffectComponent implements Component {
	
	private Map<Class<? extends Effect>, Effect> effects;
	
	public EffectComponent() {
		effects = new HashMap<Class<? extends Effect>, Effect>();
	}
	
	public void addEffect(Class<? extends Effect> clazz, Effect effect) {
		effects.put(clazz, effect);
	}
	
	public void addAllEffects(Map<Class<? extends Effect>, Effect> effects) {
		this.effects.putAll(effects);
	}
	
	public void removeEffect(Class<? extends Effect> clazz) {
		effects.remove(clazz);
	}
	
	public void removeAllEffects() {
		effects.clear();
	}
	
	public void resetEffect(Class<? extends Effect> clazz) {
		if (hasEffect(clazz))
			effects.get(clazz).reset();
	}
	
	public void resetAllEffects() {
		for (Class<? extends Effect> clazz : effects.keySet()) {
			effects.get(clazz).reset();
		}
	}
	
	public void startEffect(Class<? extends Effect> clazz) {
		effects.get(clazz).start();
	}
	
	public void startAllEffects() {
		for (Class<? extends Effect> clazz : effects.keySet()) {
			effects.get(clazz).start();
		}
	}
	
	public void updateEffect(Class<? extends Effect> clazz, Entity entity) {
		effects.get(clazz).update(entity);
	}
	
	public void updateAllEffects(Entity entity) {
		for (Class<? extends Effect> clazz : effects.keySet()) {
			effects.get(clazz).update(entity);
		}
	}
	
	public boolean hasEffect(Class<? extends Effect> clazz) {
		return effects.containsKey(clazz);
	}
}
