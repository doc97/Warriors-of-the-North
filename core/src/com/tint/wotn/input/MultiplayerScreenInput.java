package com.tint.wotn.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.tint.wotn.Core;
import com.tint.wotn.screens.MultiplayerScreen;
import com.tint.wotn.ui.MultiplayerScreenUI;
import com.tint.wotn.ui.UserInterfaces;

/**
 * An {@link InputAdapter} handling input when in
 * {@link MultiplayerScreen}
 * @author doc97
 *
 */
public class MultiplayerScreenInput extends InputAdapter {

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			((MultiplayerScreenUI) Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.MULTIPLAYER_SCREEN_UI)).back();
		}
		return false;
	}
}
