package com.tint.wotn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tint.wotn.Core;
import com.tint.wotn.input.Inputs;
import com.tint.wotn.save.GameLoader;
import com.tint.wotn.ui.UserInterfaces;

/**
 * {@link Screen} that shows the main menu
 * @author doc97
 *
 */
public class MainMenuScreen implements Screen {

	private boolean loaded;
	private Texture background;
	
	public void load() {
		if(loaded) return;
		Core.INSTANCE.inputSystem.register(Inputs.MAIN_MENU_SCREEN_UI,
				Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.MAIN_MENU_SCREEN_UI).getStage(), false);
		
		background = Core.INSTANCE.assets.getTexture("textures/notpacked/main_menu_background.png");
		loaded = true;
	}
	
	@Override
	public void show() {
		load();
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		Core.INSTANCE.inputSystem.add(Inputs.MAIN_MENU_SCREEN_UI);
		if (!Core.INSTANCE.audioSystem.musicIsCurrentlyPlaying())
			Core.INSTANCE.audioSystem.playMusic("sounds/background_music.ogg", 0.1f, true);
		
		Stage stage = Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.MAIN_MENU_SCREEN_UI).getStage();
		Core.INSTANCE.UISystem.setStage(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Core.INSTANCE.update(delta);
		
		if (Gdx.input.isKeyPressed(Keys.L)) {
			GameLoader.load("saves/save.dat");
		}

		Core.INSTANCE.batch.begin();
		Core.INSTANCE.batch.draw(background,
				0, 0,
				Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.MAIN_MENU_SCREEN_UI).getStage().getWidth(),
				Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.MAIN_MENU_SCREEN_UI).getStage().getHeight());
		Core.INSTANCE.batch.end();

		Core.INSTANCE.UISystem.draw();
		
		Core.INSTANCE.batch.begin();
		
		Core.INSTANCE.batch.draw(
				Core.INSTANCE.assets.getTexture("textures/notpacked/dark_edge.png"),
				0, 0,
				Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.MAIN_MENU_SCREEN_UI).getStage().getWidth(),
				Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.MAIN_MENU_SCREEN_UI).getStage().getHeight());
				
		Core.INSTANCE.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		Core.INSTANCE.camera.resize(width, height);
		Core.INSTANCE.camera.center();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		Core.INSTANCE.inputSystem.remove(Inputs.MAIN_MENU_SCREEN_UI);
		Core.INSTANCE.UISystem.setStage(null);
	}

	@Override
	public void dispose() {
		Core.INSTANCE.inputSystem.unregister(Inputs.MAIN_MENU_SCREEN_UI);
	}
}
