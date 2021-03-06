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
	public Vector2 offset = new Vector2();
	public Vector2 effectOffset = new Vector2();
	public Vector2 size = new Vector2();
	public Color tintColor = new Color();
	
	/**
	 * Draws an {@link AtlasRegion} that is centered at the position added by
	 * the offset and that is tinted with the tint color
	 */
	public void render(SpriteBatch batch, Vector2 position) {
		if (texture != null) {
			batch.setColor(tintColor);
			batch.draw(texture,
					position.x + offset.x + effectOffset.x - size.x / 2,
					position.y + offset.y + effectOffset.y - size.y / 2,
					size.x, size.y);
		}
	}
}
