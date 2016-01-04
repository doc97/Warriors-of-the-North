package com.tint.wotn.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.utils.CoordinateConversions;

public class GameInput implements InputProcessor {

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
		Vector2 worldCoord = CoordinateConversions.screenToWorld(screenX, screenY);
		Vector2 hexCoord = CoordinateConversions.worldToAxial(Tile.SIZE, Tile.SPACING, worldCoord.x, worldCoord.y);
		Core.INSTANCE.userControlSystem.updateWorldTouchPos(worldCoord);
		
		if(Core.INSTANCE.userControlSystem.selectUnit(hexCoord)) return false;
		if(Core.INSTANCE.userControlSystem.moveSelectedUnit(hexCoord)) return false;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Core.INSTANCE.userControlSystem.dragCamera(screenX, screenY);
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
