package com.tint.wotn.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.tint.wotn.screens.MultiplayerScreen;

/**
 * An {@link InputAdapter} handling input when in
 * {@link MultiplayerScreen}
 * @author doc97
 *
 */
public class MultiplayerScreenInput extends InputAdapter {

	private MultiplayerScreen screen;
	
	public MultiplayerScreenInput(MultiplayerScreen screen) {
		this.screen = screen;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			screen.back();
		}
		return false;
	}
}
