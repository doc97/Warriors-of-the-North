package com.tint.wotn.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Component containing:
 * <br>
 * A texture, {@link AtlasRegion},
 * <br>
 * An offset and size, {@link Vector2}
 * <br>
 * A tint color, {@link Color}
 * @author doc97
 * @see Component
 * 
 */
public class RenderComponent implements Component {
	public AtlasRegion texture;
	public Vector2 offset;
	public Vector2 size;
	public Color tintColor;
	
	/**
	 * Draws an {@link AtlasRegion} that is centered at the position added by
	 * the offset and that is tinted with the tint color
	 */
	public void render(SpriteBatch batch, Vector2 position) {
		batch.setColor(tintColor);
		batch.draw(texture, position.x + offset.x - size.x / 2, position.y + offset.y - size.y / 2, size.x, size.y);
	}
}
