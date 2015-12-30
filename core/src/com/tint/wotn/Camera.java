package com.tint.wotn;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera {
	
	public OrthographicCamera orthoCam;
	public int width, height;
	
	public Camera(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void initialize() {
		orthoCam = new OrthographicCamera(width, height);
	}
	
	public void update() {
		orthoCam.update();
	}
	
	public void size(int width, int height) {
		this.width = width;
		this.height = height;
		orthoCam.viewportWidth = width;
		orthoCam.viewportHeight = height;
	}
	
	public void set(float x, float y) {
		orthoCam.position.set(x, y, 0);
	}
	
	public void add(float dx, float dy) {
		orthoCam.position.add(dx, dy, 0);
	}
}
