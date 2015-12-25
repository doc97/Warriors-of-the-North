package com.tint.wotn.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.tint.wotn.ecs.components.AttackComponent;
import com.tint.wotn.ecs.components.HealthComponent;
import com.tint.wotn.ecs.components.PositionComponent;

public class Mappers {
	public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
	public static final ComponentMapper<HealthComponent> health = ComponentMapper.getFor(HealthComponent.class);
	public static final ComponentMapper<AttackComponent> attack = ComponentMapper.getFor(AttackComponent.class);
}
