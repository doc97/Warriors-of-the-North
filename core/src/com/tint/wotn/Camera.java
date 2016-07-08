package com.tint.wotn;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * A wrapper for the {@link OrthographicCamera} and includes a {@link Viewport} for resizing
 * @author doc97
 *
 */
public class Camera {
	
	private OrthographicCamera orthoCam;
	private Viewport viewport;
	
	public void initialize(int width, int height) {
		orthoCam = new OrthographicCamera();
		viewport = new ExtendViewport(width, height, orthoCam);
		viewport.apply();
		center();
	}
	
	public void update() {
		orthoCam.update();
	}
	
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
	
	/**
	 * Positions camera at (viewportWidth / 2, viewportHeight / 2)
	 */
	public void center() {
		orthoCam.position.set(orthoCam.viewportWidth / 2, orthoCam.viewportHeight / 2, 0);
	}
	
	public void setZoom(float zoom) {
		orthoCam.zoom = zoom;
	}
	
	public void set(float x, float y) {
		orthoCam.position.set(x, y, 0);
	}
	
	public void add(float dx, float dy) {
		orthoCam.position.add(dx, dy, 0);
	}
	
	public OrthographicCamera getOrthoCam() {
		return orthoCam;
	}
	
	public float getX() {
		return orthoCam.position.x;
	}
	
	public float getY() {
		return orthoCam.position.y;
	}
	
	public float getWidth() {
		return viewport.getWorldWidth();
	}
	
	public float getHeight() {
		return viewport.getWorldHeight();
	}
	
	public float getZoom() {
		return orthoCam.zoom;
	}
}
