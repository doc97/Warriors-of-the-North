package com.tint.wotn.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.tint.wotn.Core;

public class LoadingScreen implements Screen {

	@Override
	public void show() {
		TextureParameter texLinearParam = new TextureParameter();
		texLinearParam.magFilter = TextureFilter.Linear;
		texLinearParam.minFilter = TextureFilter.Linear;
		texLinearParam.genMipMaps = true;
		Core.INSTANCE.assetManager.load("hexagon.png", Texture.class, texLinearParam);
	}

	@Override
	public void render(float delta) {
		if(Core.INSTANCE.assetManager.update()) {
			Core.INSTANCE.screenManager.enterScreen(Screens.GAME);
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
