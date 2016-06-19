package com.tint.wotn.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.save.GameLoader;
import com.tint.wotn.screens.BattleScreen;
import com.tint.wotn.screens.Screens;
import com.tint.wotn.utils.CoordinateConversions;

/**
 * An {@link InputAdapter} handling input when in {@link BattleScreen}
 * @author doc97
 *
 */
public class BattleScreenInput extends InputAdapter {

	private Vector2 touchPos = new Vector2();
	private boolean dragging;

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACKSPACE) {
			Core.INSTANCE.screenSystem.setScreenToEnter(Screens.CAMPAIGN);
		}
		if (keycode == Keys.ENTER) {
			Core.INSTANCE.screenSystem.setScreenToEnter(Screens.END);
		}
		if (keycode == Keys.S) {
			GameLoader.save("saves/save.dat");
		}
		if (keycode == Keys.L) {
			Core.INSTANCE.screenSystem.exitCurrentScreen();
			GameLoader.load("saves/save.dat");
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.ESCAPE) {
			Core.INSTANCE.ucc.endTurn();
		}
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector2 targetWorldPos = CoordinateConversions.screenToWorldPos(screenX, screenY);
		Vector2 targetHexCoord = CoordinateConversions.worldToAxial(Tile.SIZE, Tile.SPACING, targetWorldPos.x, targetWorldPos.y);
		touchPos.set(screenX, screenY);
		Core.INSTANCE.ucc.touchTile(targetHexCoord);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		dragging = false;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		dragging = true;
		Vector2 delta = CoordinateConversions.screenToWorld(touchPos.x - screenX, touchPos.y - screenY);
		touchPos.set(screenX, screenY);
		Core.INSTANCE.ucc.dragCamera(delta.x, -delta.y);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		if(dragging) return false;
		float zoom = Core.INSTANCE.camera.orthoCam.zoom + 0.1f * amount;
		if(zoom > 0.33f && zoom < 3.0f) {
			Core.INSTANCE.camera.setZoom(zoom);
		}
		return false;
	}
}
