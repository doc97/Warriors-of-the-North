package com.tint.wotn.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class RenderComponent implements Component {
	public Texture texture;
	public Vector2 offset;
	public Vector2 size;
	public Color tintColor;
	
	public void render(SpriteBatch batch, Vector2 position) {
		batch.setColor(tintColor);
		batch.draw(texture, position.x + offset.x - size.x / 2, position.y + offset.y - size.y / 2, size.x, size.y);
	}
}
