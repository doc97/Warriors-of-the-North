package com.tint.wotn.input;

import com.badlogic.gdx.Input.Keys;
import com.tint.wotn.Core;
import com.tint.wotn.screens.Screens;
import com.badlogic.gdx.InputAdapter;

public class CampaignInput extends InputAdapter {

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACKSPACE) {
			Core.INSTANCE.screenSystem.setScreenToEnter(Screens.MAIN_MENU);
		}
		return false;
	}
}
