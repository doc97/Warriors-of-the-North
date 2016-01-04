package com.tint.wotn.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.tint.wotn.ecs.components.AttackComponent;
import com.tint.wotn.ecs.components.HealthComponent;
import com.tint.wotn.ecs.components.MovementComponent;
import com.tint.wotn.ecs.components.RenderComponent;

public class Mappers {
	public static final ComponentMapper<MovementComponent> position = ComponentMapper.getFor(MovementComponent.class);
	public static final ComponentMapper<HealthComponent> health = ComponentMapper.getFor(HealthComponent.class);
	public static final ComponentMapper<AttackComponent> attack = ComponentMapper.getFor(AttackComponent.class);
	public static final ComponentMapper<RenderComponent> render = ComponentMapper.getFor(RenderComponent.class);
}
