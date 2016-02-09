package com.tint.wotn.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Component containing:
 * <br>
 * A position, {@link Vector2}
 * <br>
 * A range value
 * <br>
 * A cost value
 * @author doc97
 * @see Component
 * 
 */
public class MovementComponent implements Component {
	public Vector2 position;
	public int range;
	public int cost;
}
