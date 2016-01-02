package com.tint.wotn.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.utils.CoordinateConversions;

public class GameInput implements InputProcessor {

	public Vector2 worldTouchPos = new Vector2();
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		worldTouchPos.set(CoordinateConversions.screenToWorld(screenX, screenY));
		Vector2 hexCoord = CoordinateConversions.worldToAxial(Tile.SIZE, Tile.SPACING, worldTouchPos.x, worldTouchPos.y);
		Tile t = Core.INSTANCE.levelSystem.getCurrentLevel().map.getTile((int) hexCoord.x, (int) hexCoord.y);
		System.out.println(t);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector2 currentTouchPos = CoordinateConversions.screenToWorld(screenX, screenY);
		Vector2 delta = worldTouchPos.cpy().sub(currentTouchPos);
		Core.INSTANCE.camera.add(delta.x, delta.y);
		Core.INSTANCE.camera.update();
		worldTouchPos = new Vector2(currentTouchPos.x, currentTouchPos.y);

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
