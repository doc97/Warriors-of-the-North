package com.tint.wotn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tint.wotn.Core;
import com.tint.wotn.GameMode;

public class MainMenuScreen implements Screen {

	private Stage stage;
	
	public MainMenuScreen() {
		stage = new Stage(Core.INSTANCE.camera.viewport);
	}
	
	@Override
	public void show() {
		Skin skin = new Skin(Gdx.files.internal("skins/default/uiskin.json"));
		
		// Widgets
//		Image title = new Image(titleTexture);
		Label title = new Label("Warriors of the North", skin);
		title.setFontScale(8);
		
		TextButton singleplayerBtn = new TextButton("Singleplayer", skin);
		singleplayerBtn.getLabel().setFontScale(4);
		singleplayerBtn.pad(10);
		singleplayerBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Core.INSTANCE.gameMode = GameMode.SINGLEPLAYER;
				Core.INSTANCE.initializeGame();
				Core.INSTANCE.screenSystem.enterScreen(Screens.GAME);
			}
		});
		
		TextButton multiplayerBtn = new TextButton("Multiplayer", skin);
		multiplayerBtn.getLabel().setFontScale(4);
		multiplayerBtn.pad(10);
		multiplayerBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Core.INSTANCE.gameMode = GameMode.MULTIPLAYER;
				Core.INSTANCE.initializeGame();
				Core.INSTANCE.screenSystem.enterScreen(Screens.MULTIPLAYER);
			}
		});

		TextButton optionsBtn = new TextButton("Options", skin);
		optionsBtn.getLabel().setFontScale(4);
		optionsBtn.pad(10);
		
		TextButton exitBtn = new TextButton("Exit", skin);
		exitBtn.getLabel().setFontScale(4);
		exitBtn.pad(10);
		exitBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

		Table table = new Table();
		table.debug();
		table.setFillParent(true);

		table.add(title).expandY();
		table.row();
		table.add(singleplayerBtn).padTop(50).expandY();
		table.row();
		table.add(multiplayerBtn).expandY();
		table.row();
		table.add(optionsBtn).expandY();
		table.row();
		table.add(exitBtn).padBottom(50).expandY();
	
		stage.addActor(table);
		
		Gdx.input.setInputProcessor(stage);
		
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
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
