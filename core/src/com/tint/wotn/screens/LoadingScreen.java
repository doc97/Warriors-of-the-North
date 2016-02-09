package com.tint.wotn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tint.wotn.Core;
import com.tint.wotn.GameMode;

/**
 * {@link Screen} that is showed when loading game files
 * @author doc97
 *
 */
public class LoadingScreen implements Screen {

	@Override
	public void show() {
		Core.INSTANCE.assets.loadAssets();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!Core.INSTANCE.assets.isDoneLoading()) {
			Core.INSTANCE.assets.updateLoading();
		} else {
			Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
			Core.INSTANCE.assets.loadTexturesIntoGame();
			if(Core.INSTANCE.gameMode == GameMode.SINGLEPLAYER) {
				Core.INSTANCE.screenSystem.enterScreen(Screens.MAIN_MENU);
			} else if(Core.INSTANCE.gameMode == GameMode.MULTIPLAYER) {
				if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
					Core.INSTANCE.screenSystem.enterScreen(Screens.MULTIPLAYER);
				}
			}
		}
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
		
	}

	@Override
	public void dispose() {
		
	}
}
