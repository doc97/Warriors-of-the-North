package com.tint.wotn.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.tint.wotn.ecs.components.AttackComponent;
import com.tint.wotn.ecs.components.HealthComponent;
import com.tint.wotn.ecs.components.IDComponent;
import com.tint.wotn.ecs.components.MovementComponent;
import com.tint.wotn.ecs.components.OwnerComponent;
import com.tint.wotn.ecs.components.RenderComponent;
import com.badlogic.ashley.core.Component;

/**
 * Contains {@link ComponentMapper}'s for fast access by component
 * @author doc97
 * @see Component
 * 
 */
public class Mappers {
	public static final ComponentMapper<MovementComponent> movement = ComponentMapper.getFor(MovementComponent.class);
	public static final ComponentMapper<HealthComponent> health = ComponentMapper.getFor(HealthComponent.class);
	public static final ComponentMapper<AttackComponent> attack = ComponentMapper.getFor(AttackComponent.class);
	public static final ComponentMapper<RenderComponent> render = ComponentMapper.getFor(RenderComponent.class);
	public static final ComponentMapper<OwnerComponent> owner = ComponentMapper.getFor(OwnerComponent.class);
	public static final ComponentMapper<IDComponent> id = ComponentMapper.getFor(IDComponent.class);
}
