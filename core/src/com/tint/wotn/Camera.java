package com.tint.wotn;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Camera {
	
	public OrthographicCamera orthoCam;
	public Viewport viewport;
	
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
}
