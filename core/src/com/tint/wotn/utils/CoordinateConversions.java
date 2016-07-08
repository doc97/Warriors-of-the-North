package com.tint.wotn.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tint.wotn.Core;
import com.tint.wotn.levels.maps.Tile;

/**
 * Utility class for converting between coordination systems like cube and
 * axial used in hex space and as well with screen and world coordinates
 * @author doc97
 *
 */
public class CoordinateConversions {
	private static final double SQRT_THREE = Math.sqrt(3);

	/**
	 * Converts an axial coordinate to world space, check {@link Tile} for
	 * constants
	 * @param size Tile size
	 * @param spacing Spacing between tiles
	 * @param axial Coordinate in axial coordinate space
	 * @return The converted world coordinate
	 */
	public static Vector2 axialToWorld(float size, int spacing, Vector2 axial) {
		float x = (float) (((size + spacing) * SQRT_THREE) * (axial.x + axial.y / 2.0f));
		float y = (float) (((size + spacing) * 3.0f / 2.0f) * axial.y);
		return new Vector2(x, y);
	}

	/**
	 * Converts a cube coordinate to world space, check {@link Tile} for
	 * constants
	 * @param size Tile size constant
	 * @param spacing Spacing constant, space between tiles
	 * @param cube Coordinate in cube coordinate space
	 * @return The converted world coordinate
	 */
	public static Vector2 cubeToWorld(float size, int spacing, Vector3 cube) {
		Vector2 axial = HexCoordinates.transform(cube);
		return axialToWorld(size, spacing, axial);
	}
	
	/**
	 * Converts a world coordinate to axial space, check {@link Tile} for
	 * constants
	 * @param size Tile size constant
	 * @param spacing Spacing constant, space between tiles
	 * @param x World coordinate's x-component
	 * @param y World coordinate's y-component
	 * @return The converted axial coordinate
	 */
	public static Vector2 worldToAxial(float size, int spacing, float x, float y) {
		Vector3 cube = worldToCube(size, spacing, x, y);
		return HexCoordinates.transform(cube);
	}

	/**
	 * Converts a world coordinate to cube space, check {@link Tile} for
	 * constants
	 * @param size Tile size constant
	 * @param spacing Spacing constant, space between tiles
 	 * @param x World coordinate's x-component
	 * @param y World coordinate's y-component
	 * @return The converted cube coordinate
	 */
	public static Vector3 worldToCube(float size, int spacing, float x, float y) {
		float q = (float) ((x * SQRT_THREE - y) / 3.0f) / (size + spacing);
		float r = (y * 2.0f / 3.0f) / (size + spacing);
		return HexCoordinates.cubeRound(HexCoordinates.transform(new Vector2(q, r)));
	}
	
	/**
	 * Converts a screen coordinate to world space and taking camera position
	 * into consideration, cameras position is in world space in the center
	 * of the screen
	 * @param screenX Screen coordinate's x-component
	 * @param screenY Screen coordinate's y-component
	 * @return Converted world coordinate
	 */
	public static Vector2 screenToWorldPos(float screenX, float screenY) {
		Vector2 worldPos = screenToWorld(screenX, Gdx.graphics.getHeight() - screenY);

		worldPos.x -= Core.INSTANCE.camera.getWidth() / 2 * Core.INSTANCE.camera.getZoom();
		worldPos.y -= Core.INSTANCE.camera.getHeight() / 2 * Core.INSTANCE.camera.getZoom();
		worldPos.x += Core.INSTANCE.camera.getX();
		worldPos.y += Core.INSTANCE.camera.getY();
		return worldPos;
	}
	
	/**
	 * Converts a world coordinate to screen space and taking camera position
	 * into consideration, cameras position is in world space in the center
	 * of the screen
	 * @param worldX World coordinate's x-component
	 * @param worldY World coordinate's y-component
	 * @return Converted screen coordinate
	 */
	public static Vector2 worldToScreenPos(float worldX, float worldY) {
		worldX -= Core.INSTANCE.camera.getX();
		worldY -= Core.INSTANCE.camera.getY();
		worldX += Core.INSTANCE.camera.getWidth() / 2 * Core.INSTANCE.camera.getZoom();
		worldY += Core.INSTANCE.camera.getHeight() / 2 * Core.INSTANCE.camera.getZoom();
		Vector2 screenPos = worldToScreen(worldX, worldY);
		return screenPos;
	}
	
	/**
	 * Converts world coordinates to screen space, without taking the camera
	 * position into consideration
	 * @param worldX World coordinate's x-component
	 * @param worldY World coordinate's y-component
	 * @return Converted screen coordinate
	 */
	private static Vector2 worldToScreen(float worldX, float worldY) {
		float screenX = (worldX / (float) (Core.INSTANCE.camera.getWidth() * Core.INSTANCE.camera.getZoom())) * Gdx.graphics.getWidth();
		float screenY = (-worldY / (float) (Core.INSTANCE.camera.getHeight() * Core.INSTANCE.camera.getZoom())) * Gdx.graphics.getHeight() + Gdx.graphics.getHeight();
		return new Vector2(screenX, screenY);
	}
	
	/**
	 * Converts screen coordinate to world space, without taking the camera
	 * position into consideration
	 * @param screenX Screen coordinate's x-component
	 * @param screenY Screen coordinate's y-component
	 * @return Converted world coordinate
	 */
	public static Vector2 screenToWorld(float screenX, float screenY) {
		float worldX = (screenX / (float) Gdx.graphics.getWidth()) * Core.INSTANCE.camera.getWidth();
		worldX *= Core.INSTANCE.camera.getZoom();
		float worldY = (screenY / (float) Gdx.graphics.getHeight()) * Core.INSTANCE.camera.getHeight();
		worldY *= Core.INSTANCE.camera.getZoom();
		return new Vector2(worldX, worldY);
	}
}
