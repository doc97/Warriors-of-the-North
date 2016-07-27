package com.tint.wotn.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
		stage.clear();


		ImageTextButtonStyle singleplayerBtnStyle = getButtonStyle();
		final ImageTextButton singleplayerBtn = new ImageTextButton("Singleplayer", singleplayerBtnStyle);
		singleplayerBtn.getLabelCell().padBottom(singleplayerBtnStyle.font.getCapHeight() / 2);
		singleplayerBtn.pad(10);
		singleplayerBtn.addListener(new InputListener() {
			boolean valid;

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				valid = true;
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (valid) {
					Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
					Core.INSTANCE.gameMode = GameMode.SINGLEPLAYER;
					UIDataStorage storage = Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.CAMPAIGN_SCREEN_UI).getStorage();
					storage.storeData("Data", "Page text", Core.INSTANCE.story.getCurrentPage().getText());
					storage.storeData("Data", "Page visible", "true");
					Core.INSTANCE.screenSystem.setScreenToEnter(Screens.CAMPAIGN);
				}
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				valid = false;
			}
		});
		
		ImageTextButtonStyle multiplayerBtnStyle = getButtonStyle();
		ImageTextButton multiplayerBtn = new ImageTextButton("Multiplayer", multiplayerBtnStyle);
		multiplayerBtn.getLabelCell().padBottom(multiplayerBtnStyle.font.getCapHeight() / 2);
		multiplayerBtn.pad(10);
		multiplayerBtn.addListener(new InputListener() {
			boolean valid;
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (valid) {
					Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
					Core.INSTANCE.gameMode = GameMode.MULTIPLAYER;
					Core.INSTANCE.initializeGame();
					Core.INSTANCE.screenSystem.setScreenToEnter(Screens.MULTIPLAYER);
				}
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				valid = false;
			}
		});

		ImageTextButtonStyle optionsBtnStyle = getButtonStyle();
		ImageTextButton optionsBtn = new ImageTextButton("Options", optionsBtnStyle);
		optionsBtn.getLabelCell().padBottom(optionsBtnStyle.font.getCapHeight() / 2);
		optionsBtn.pad(10);
		optionsBtn.addListener(new InputListener() {
			boolean valid;
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				valid = true;
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (valid) {
					Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
				}
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				valid = false;
			}
		});
		
		ImageTextButtonStyle exitBtnStyle = getButtonStyle();
		ImageTextButton exitBtn = new ImageTextButton("Exit", exitBtnStyle);
		exitBtn.getLabelCell().padBottom(exitBtnStyle.font.getCapHeight() / 2);
		exitBtn.pad(10);
		exitBtn.addListener(new InputListener() {
			boolean valid;
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				valid = true;
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (valid) {
					Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
					Gdx.app.exit();
				}
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				valid = false;
			}
		});

		Table table = new Table();
		table.setFillParent(true);

		table.add().expandY();
		table.row();
		table.add(singleplayerBtn);
		table.row();
		table.add(multiplayerBtn);
		table.row();
		table.add(optionsBtn);
		table.row();
		table.add(exitBtn).padBottom(100);
	
		stage.addActor(table);
	}
	
	private ImageTextButtonStyle getButtonStyle() {
		ImageTextButtonStyle btnStyle = new ImageTextButtonStyle();
		TextureAtlas atlas = Core.INSTANCE.assets.getTextureAtlas("textures/packed/WarriorsOfTheNorth.atlas");
		TextureRegionDrawable normal = new TextureRegionDrawable(atlas.findRegion("menu_button"));
		TextureRegionDrawable over = new TextureRegionDrawable(atlas.findRegion("menu_button_over"));
		btnStyle.font = Core.INSTANCE.assets.getFont("menu.ttf");
		btnStyle.fontColor = Color.BLACK;
		btnStyle.downFontColor = Color.WHITE;
		btnStyle.over = over;
		btnStyle.up = normal;
		btnStyle.down = normal;
		
		return btnStyle;
	}
}
