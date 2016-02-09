package com.tint.wotn.ecs.components;

import com.badlogic.ashley.core.Component;

/**
 * Component containing a damage and a cost value
 * @author doc97
 * @see Component
 * 
 */
public class AttackComponent implements Component {
	public int damage = 1;
	public int cost;
}
