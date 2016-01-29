package com.tint.wotn.input;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.utils.CoordinateConversions;

public class GestureInput implements GestureListener {

	public GestureDetector detector = new GestureDetector(this);
	private float currentZoomDistance;
	private float currentInitialDistance;
	
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Vector2 targetWorldPos = CoordinateConversions.screenToWorldPos(x, y);
		Vector2 targetHexCoord = CoordinateConversions.worldToAxial(Tile.SIZE, Tile.SPACING, targetWorldPos.x, targetWorldPos.y);
		Core.INSTANCE.userControlSystem.touchTile(targetHexCoord);
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		Core.INSTANCE.userControlSystem.endTurn();
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		Core.INSTANCE.userControlSystem.dragCamera(-deltaX, deltaY);
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		if(currentInitialDistance != initialDistance) {
			currentZoomDistance = initialDistance;
			currentInitialDistance = initialDistance;
		}

		float zoomIn = 0.0005f * (currentZoomDistance - distance);
		float zoom = Core.INSTANCE.camera.orthoCam.zoom + zoomIn;
		if(zoom > 0.33f && zoom < 3.0f) {
			Core.INSTANCE.camera.setZoom(zoom);
		}
		
		currentZoomDistance = distance;
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
}