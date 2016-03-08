package com.tint.wotn.input;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.screens.BattleScreen;
import com.tint.wotn.screens.Screens;
import com.tint.wotn.utils.CoordinateConversions;

/**
 * A {@link GestureListener} handling touch screen input in
 * {@link BattleScreen}
 * @author doc97
 *
 */
public class GestureInput implements GestureListener {

	public GestureDetector detector;
	private float currentZoomDistance;
	private float currentInitialDistance;
	private boolean zooming;
	
	public GestureInput() {
		detector = new GestureDetector(this);
		detector.setTapCountInterval(0.2f);
		detector.setTapSquareSize(50);
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Vector2 targetWorldPos = CoordinateConversions.screenToWorldPos(x, y);
		Vector2 targetHexCoord = CoordinateConversions.worldToAxial(Tile.SIZE, Tile.SPACING, targetWorldPos.x, targetWorldPos.y);
		Core.INSTANCE.userControlSystem.touchTile(targetHexCoord);
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		if(count == 2)
			Core.INSTANCE.camera.center();
		if (button == Buttons.BACK)
			Core.INSTANCE.screenSystem.setScreenToEnter(Screens.CAMPAIGN);

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
		if(zooming) return false;
		Vector2 delta = CoordinateConversions.screenToWorld(deltaX, deltaY);
		Core.INSTANCE.userControlSystem.dragCamera(-delta.x, delta.y);
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		zooming = false;
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		zooming = true;
		if(currentInitialDistance != initialDistance) {
			currentZoomDistance = initialDistance;
			currentInitialDistance = initialDistance;
		}

		float zoomIn = 0.001f * (currentZoomDistance - distance);
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
