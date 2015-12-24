package com.tint.wotn;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera {
	
	private OrthographicCamera camera;
	private int width, height;
	
	public Camera(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void initialize() {
		camera = new OrthographicCamera(width, height);
		set(100, 100);
	}
	
	public void update() {
		camera.update();
	}
	
	public void size(int width, int height) {
		this.width = width;
		this.height = height;
		camera.viewportWidth = width;
		camera.viewportHeight = height;
	}
	
	public void set(float x, float y) {
		camera.position.set(x, y, 0);
	}
	
	public void add(float dx, float dy) {
		camera.position.add(dx, dy, 0);
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public float getX() {
		return camera.position.x;
	}
	
	public float getY() {
		return camera.position.y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
}
