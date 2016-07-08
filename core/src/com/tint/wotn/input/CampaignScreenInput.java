package com.tint.wotn.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.tint.wotn.Core;
import com.tint.wotn.screens.Screens;

public class CampaignScreenInput extends InputAdapter {

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACKSPACE) {
			Core.INSTANCE.screenSystem.setScreenToEnter(Screens.MAIN_MENU);
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.L) {
			Core.INSTANCE.missionSystem.initialize();
			Core.INSTANCE.world.initialize();
			return true;
		}
		
		return false;
	}
}