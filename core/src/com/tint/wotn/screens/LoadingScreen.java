package com.tint.wotn.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.tint.wotn.Core;
import com.tint.wotn.levels.maps.HexMap;
import com.tint.wotn.utils.AssetLoader;

public class LoadingScreen implements Screen {

	@Override
	public void show() {
		String textureFile = "Core.wotn_tex";
		try {
			AssetLoader.loadTextures(textureFile);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to load textures from file: " + textureFile);
			Gdx.app.exit();
		}
	}

	@Override
	public void render(float delta) {
		if(Core.INSTANCE.assetManager.update()) {
			HexMap.loadTextures();
			Core.INSTANCE.levelSystem.enterLevel(0);
			Core.INSTANCE.screenSystem.enterScreen(Screens.GAME);
		}
		
		float progress = Core.INSTANCE.assetManager.getProgress();
		System.out.println(progress * 100.0f + "%");
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
