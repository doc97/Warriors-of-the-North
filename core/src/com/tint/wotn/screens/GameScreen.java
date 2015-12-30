package com.tint.wotn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tint.wotn.Core;

public class GameScreen implements Screen {
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Core.INSTANCE.camera.update();
		Core.INSTANCE.batch.setProjectionMatrix(Core.INSTANCE.camera.getCamera().combined);
		
		Core.INSTANCE.batch.begin();
		Core.INSTANCE.levelSystem.getCurrentLevel().render(Core.INSTANCE.batch);
		Core.INSTANCE.batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void resume() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void show() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
	}
	
	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}
}
