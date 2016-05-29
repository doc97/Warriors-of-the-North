package com.tint.wotn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tint.wotn.Core;
import com.tint.wotn.input.Inputs;
import com.tint.wotn.input.LobbyScreenInput;
import com.tint.wotn.ui.UserInterfaces;

/**
 * {@link Screen} that represents a multiplayer lobby
 * @author doc97
 *
 */
public class LobbyScreen implements Screen {

	private boolean loaded;
	
	public void load() {
		if (loaded) return;
		Core.INSTANCE.inputSystem.register(Inputs.LOBBY_SCREEN, new LobbyScreenInput(), false);
		Core.INSTANCE.inputSystem.register(Inputs.LOBBY_SCREEN_UI,
				Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.LOBBY_SCREEN_UI).getStage(), false);
		loaded = true;
	}

	@Override
	public void show() {
		load();
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
		Core.INSTANCE.inputSystem.add(Inputs.LOBBY_SCREEN);
		Core.INSTANCE.inputSystem.add(Inputs.LOBBY_SCREEN_UI);
		
		Stage stage = Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.LOBBY_SCREEN_UI).getStage();
		Core.INSTANCE.UISystem.setStage(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Core.INSTANCE.screenSystem.update();
		
		Core.INSTANCE.UISystem.draw();
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
		Core.INSTANCE.inputSystem.remove(Inputs.LOBBY_SCREEN);
		Core.INSTANCE.inputSystem.remove(Inputs.LOBBY_SCREEN_UI);
		Core.INSTANCE.UISystem.setStage(null);
	}

	@Override
	public void dispose() {
		Core.INSTANCE.inputSystem.unregister(Inputs.LOBBY_SCREEN);
		Core.INSTANCE.inputSystem.unregister(Inputs.LOBBY_SCREEN_UI);
	}
}
