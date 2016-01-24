package com.tint.wotn.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.utils.CoordinateConversions;

public class GameInput implements InputProcessor {

	private Vector2 worldTouchPos = new Vector2();
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.ESCAPE) {
			Core.INSTANCE.userControlSystem.endTurn();
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector2 targetWorldPos = CoordinateConversions.screenToWorldPos(screenX, screenY);
		Vector2 targetHexCoord = CoordinateConversions.worldToAxial(Tile.SIZE, Tile.SPACING, targetWorldPos.x, targetWorldPos.y);
		worldTouchPos = targetWorldPos;
		Core.INSTANCE.userControlSystem.touchTile(targetHexCoord);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector2 currentTouchPos = CoordinateConversions.screenToWorldPos(screenX, screenY);
		Vector2 delta = worldTouchPos.cpy().sub(currentTouchPos);
		Core.INSTANCE.userControlSystem.dragCamera(delta.x, delta.y);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		float zoom = Core.INSTANCE.camera.orthoCam.zoom + 0.1f * amount;
		if(zoom > 0.33f && zoom < 3.0f) {
			Core.INSTANCE.camera.setZoom(zoom);
		}
		return false;
	}
}
