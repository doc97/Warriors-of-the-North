package com.tint.wotn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.tint.wotn.Core;
import com.tint.wotn.input.Inputs;

public class GameScreen implements Screen {
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Core.INSTANCE.camera.update();
		Core.INSTANCE.batch.setProjectionMatrix(Core.INSTANCE.camera.orthoCam.combined);
		
		Core.INSTANCE.batch.begin();
		Core.INSTANCE.levelSystem.getCurrentLevel().render(Core.INSTANCE.batch);
		Core.INSTANCE.batch.end();
		
		// Debug
		Core.INSTANCE.camera.update();
		Core.INSTANCE.shapeRenderer.setProjectionMatrix(Core.INSTANCE.camera.orthoCam.combined);
		
		Core.INSTANCE.shapeRenderer.begin(ShapeType.Line);
		Core.INSTANCE.shapeRenderer.line(
				Core.INSTANCE.camera.orthoCam.position.x,
				Core.INSTANCE.camera.orthoCam.position.y - Core.INSTANCE.camera.height / 2,
				Core.INSTANCE.camera.orthoCam.position.x,
				Core.INSTANCE.camera.orthoCam.position.y + Core.INSTANCE.camera.height / 2
				);
		Core.INSTANCE.shapeRenderer.line(
				Core.INSTANCE.camera.orthoCam.position.x - Core.INSTANCE.camera.width / 2,
				Core.INSTANCE.camera.orthoCam.position.y,
				Core.INSTANCE.camera.orthoCam.position.x + Core.INSTANCE.camera.width / 2,
				Core.INSTANCE.camera.orthoCam.position.y
				);
		Core.INSTANCE.shapeRenderer.end();
		
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
		Gdx.input.setInputProcessor(Core.INSTANCE.inputSystem.getProcessor(Inputs.GAME));
	}
	
	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}
}
