package com.tint.wotn.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tint.wotn.Core;
import com.tint.wotn.GameMode;
import com.tint.wotn.net.Player;
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
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(Core.INSTANCE.assetManager.update()) {
			Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
			AssetLoader.loadTexturesIntoGame();
			if(Core.INSTANCE.gameMode == GameMode.SINGLE_PLAYER) {
				Core.INSTANCE.levelSystem.enterLevel(0);
				Core.INSTANCE.screenSystem.enterScreen(Screens.GAME);
			} else if(Core.INSTANCE.gameMode == GameMode.MULTI_PLAYER) {
				if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
					try {
						Player player = new Player();
						player.name = "Bob";
						Core.INSTANCE.multiplayerSystem.connect(player, "localhost", 6666);
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("Failed to connect to server!");
					}
				}
				
				Core.INSTANCE.screenSystem.update();
				//Core.INSTANCE.screenSystem.enterScreen(Screens.LOBBY);
			}
		} else {
			float progress = Core.INSTANCE.assetManager.getProgress();
			System.out.println(progress * 100.0f + "%");
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
