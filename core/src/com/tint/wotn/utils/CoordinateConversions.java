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
		float worldX = (screenX / (float) Gdx.graphics.getWidth()) * Core.INSTANCE.camera.orthoCam.viewportWidth;
		float worldY = ((float) (Gdx.graphics.getHeight() - screenY) / Gdx.graphics.getHeight()) * Core.INSTANCE.camera.orthoCam.viewportHeight;
		worldX -= Core.INSTANCE.camera.orthoCam.viewportWidth / 2;
		worldY -= Core.INSTANCE.camera.orthoCam.viewportHeight / 2;
		worldX += Core.INSTANCE.camera.orthoCam.position.x;
		worldY += Core.INSTANCE.camera.orthoCam.position.y;
		return new Vector2(worldX, worldY);
	}
	
	public static Vector2 worldToScreenPos(float worldX, float worldY) {
		worldX -= Core.INSTANCE.camera.orthoCam.position.x;
		worldY -= Core.INSTANCE.camera.orthoCam.position.y;
		worldX += Core.INSTANCE.camera.orthoCam.viewportWidth / 2;
		worldY += Core.INSTANCE.camera.orthoCam.viewportHeight / 2;
		float screenX = (worldX / (float) Core.INSTANCE.camera.orthoCam.viewportWidth) * Gdx.graphics.getWidth();
		float screenY = (-worldY / (float) Core.INSTANCE.camera.orthoCam.viewportHeight) * Gdx.graphics.getHeight() + Gdx.graphics.getHeight();
		return new Vector2(screenX, screenY);
	}
	
	public static Vector2 screenToWorld(float screenX, float screenY) {
		float worldX = (screenX / (float) Gdx.graphics.getWidth()) * Core.INSTANCE.camera.orthoCam.viewportWidth;
		float worldY = ((Gdx.graphics.getHeight() - screenY) / (float) Gdx.graphics.getHeight()) * Core.INSTANCE.camera.orthoCam.viewportHeight;
		return new Vector2(worldX, worldY);
	}
	
	public static Vector2 worldToScreen(float worldX, float worldY) {
		float screenX = (worldX / Core.INSTANCE.camera.orthoCam.viewportWidth) * Gdx.graphics.getWidth();
		float screenY = Gdx.graphics.getHeight() - (worldY / Core.INSTANCE.camera.orthoCam.viewportHeight) * Gdx.graphics.getHeight();
		return new Vector2(screenX, screenY);
	}
}
