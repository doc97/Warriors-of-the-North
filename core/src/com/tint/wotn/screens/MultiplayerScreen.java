package com.tint.wotn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tint.wotn.Core;
import com.tint.wotn.input.Inputs;
import com.tint.wotn.input.MultiplayerScreenInput;
import com.tint.wotn.ui.UserInterfaces;

/**
 * {@link Screen} that is used for configuring multiplayer
 * @author doc97
 *
 */
public class MultiplayerScreen implements Screen {

	private boolean loaded;

	public void load() {
		if(loaded) return;
		Core.INSTANCE.inputSystem.register(Inputs.MULTIPLAYER_SCREEN, new MultiplayerScreenInput(), false);
		Core.INSTANCE.inputSystem.register(Inputs.MULTIPLAYER_SCREEN_UI,
				Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.MULTIPLAYER_SCREEN_UI).getStage(), false);

		loaded = true;
	}

	@Override
	public void show() {
		load();
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		Core.INSTANCE.inputSystem.add(Inputs.MULTIPLAYER_SCREEN);
		Core.INSTANCE.inputSystem.add(Inputs.MULTIPLAYER_SCREEN_UI);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Core.INSTANCE.screenSystem.update();
		Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.MULTIPLAYER_SCREEN_UI).render();
	}
	

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		Core.INSTANCE.inputSystem.remove(Inputs.MULTIPLAYER_SCREEN);
		Core.INSTANCE.inputSystem.remove(Inputs.MAIN_MENU_SCREEN_UI);
	}

	@Override
	public void dispose() {
		Core.INSTANCE.inputSystem.unregister(Inputs.MULTIPLAYER_SCREEN);
		Core.INSTANCE.inputSystem.unregister(Inputs.MULTIPLAYER_SCREEN_UI);
	}
}
