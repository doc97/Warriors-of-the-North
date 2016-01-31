package com.tint.wotn.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tint.wotn.Core;

public class CoordinateConversions {
	private static final double SQRT_THREE = Math.sqrt(3);

	public static Vector2 axialToWorld(float size, int spacing, Vector2 axial) {
		float x = (float) (((size + spacing) * SQRT_THREE) * (axial.x + axial.y / 2.0f));
		float y = (float) (((size + spacing) * 3.0f / 2.0f) * axial.y);
		return new Vector2(x, y);
	}

	public static Vector2 cubeToWorld(float size, int spacing, Vector3 cube) {
		Vector2 axial = HexCoordinates.transform(cube);
		return axialToWorld(size, spacing, axial);
	}
	
	public static Vector2 worldToAxial(float size, int spacing, float x, float y) {
		Vector3 cube = worldToCube(size, spacing, x, y);
		return HexCoordinates.transform(cube);
	}
	
	public static Vector3 worldToCube(float size, int spacing, float x, float y) {
		float q = (float) ((x * SQRT_THREE - y) / 3.0f) / (size + spacing);
		float r = (y * 2.0f / 3.0f) / (size + spacing);
		return HexCoordinates.cubeRound(HexCoordinates.transform(new Vector2(q, r)));
	}
	
	public static Vector2 screenToWorldPos(float screenX, float screenY) {
		Vector2 worldPos = screenToWorld(screenX, screenY);

		worldPos.x -= Core.INSTANCE.camera.orthoCam.viewportWidth / 2 * Core.INSTANCE.camera.orthoCam.zoom;
		worldPos.y -= Core.INSTANCE.camera.orthoCam.viewportHeight / 2 * Core.INSTANCE.camera.orthoCam.zoom;
		worldPos.x += Core.INSTANCE.camera.orthoCam.position.x;
		worldPos.y += Core.INSTANCE.camera.orthoCam.position.y;
		return worldPos;
	}
	
	public static Vector2 worldToScreenPos(float worldX, float worldY) {
		worldX -= Core.INSTANCE.camera.orthoCam.position.x;
		worldY -= Core.INSTANCE.camera.orthoCam.position.y;
		worldX += Core.INSTANCE.camera.orthoCam.viewportWidth / 2 * Core.INSTANCE.camera.orthoCam.zoom;
		worldY += Core.INSTANCE.camera.orthoCam.viewportHeight / 2 * Core.INSTANCE.camera.orthoCam.zoom;
		Vector2 screenPos = worldToScreen(worldX, worldY);
		return screenPos;
	}
	
	private static Vector2 screenToWorld(float screenX, float screenY) {
		float worldX = (screenX / (float) Gdx.graphics.getWidth()) * Core.INSTANCE.camera.orthoCam.viewportWidth;
		worldX *= Core.INSTANCE.camera.orthoCam.zoom;
		float worldY = ((Gdx.graphics.getHeight() - screenY) / (float) Gdx.graphics.getHeight()) * Core.INSTANCE.camera.orthoCam.viewportHeight;
		worldY *= Core.INSTANCE.camera.orthoCam.zoom;
		return new Vector2(worldX, worldY);
	}
	
	private static Vector2 worldToScreen(float worldX, float worldY) {
		float screenX = (worldX / (float) (Core.INSTANCE.camera.orthoCam.viewportWidth * Core.INSTANCE.camera.orthoCam.zoom)) * Gdx.graphics.getWidth();
		float screenY = (-worldY / (float) (Core.INSTANCE.camera.orthoCam.viewportHeight * Core.INSTANCE.camera.orthoCam.zoom)) * Gdx.graphics.getHeight() + Gdx.graphics.getHeight();
		return new Vector2(screenX, screenY);
	}
	
	public static Vector2 convertWorldToScreen(float worldX, float worldY) {
		float screenX = (worldX / (float) (Core.INSTANCE.camera.orthoCam.viewportWidth * Core.INSTANCE.camera.orthoCam.zoom)) * Gdx.graphics.getWidth();
		float screenY = (worldY / (float) (Core.INSTANCE.camera.orthoCam.viewportHeight * Core.INSTANCE.camera.orthoCam.zoom)) * Gdx.graphics.getHeight();
		return new Vector2(screenX, screenY);
	}
	
	public static Vector2 convertScreenToWorld(float screenX, float screenY) {
		float worldX = (screenX / (float) Gdx.graphics.getWidth()) * Core.INSTANCE.camera.orthoCam.viewportWidth;
		worldX *= Core.INSTANCE.camera.orthoCam.zoom;
		float worldY = (screenY / (float) Gdx.graphics.getHeight()) * Core.INSTANCE.camera.orthoCam.viewportHeight;
		worldY *= Core.INSTANCE.camera.orthoCam.zoom;
		return new Vector2(worldX, worldY);
	}
}
