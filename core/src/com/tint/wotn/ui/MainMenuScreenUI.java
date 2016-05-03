package com.tint.wotn.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tint.wotn.Core;
import com.tint.wotn.GameMode;
import com.tint.wotn.screens.Screens;

public class MainMenuScreenUI extends UserInterface {

	public MainMenuScreenUI(Skin skin) {
		super(skin);
		stage = new Stage(new ExtendViewport(1920, 1080), Core.INSTANCE.batch);
	}
	
	@Override
	public void load() {
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
				Core.INSTANCE.screenSystem.setScreenToEnter(Screens.CAMPAIGN);
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
				Core.INSTANCE.screenSystem.setScreenToEnter(Screens.MULTIPLAYER);
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
	}
}
