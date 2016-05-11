package com.tint.wotn.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tint.wotn.Core;
import com.tint.wotn.screens.Screens;

public class EndScreenUI extends UserInterface {

	public EndScreenUI(Skin skin) {
		super(skin);
		stage = new Stage(new ExtendViewport(1920, 1080), Core.INSTANCE.batch);
	}
	
	@Override
	public void load() {
		stage.clear();
		
		Label title = new Label("END SCREEN", skin);
		title.setFontScale(10);
		
		Label winner = new Label("Winner: " + "user dummy", skin);
		winner.setFontScale(5);
		
		TextButton exitBtn = new TextButton("Ok... ;C", skin);
		exitBtn.getLabel().setFontScale(4);
		exitBtn.pad(10);
		exitBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
				Core.INSTANCE.screenSystem.setScreenToEnter(Screens.CAMPAIGN);
			}
		});
		
		Table rootTable = new Table(skin);
		rootTable.debug();
		rootTable.setFillParent(true);
		
		rootTable.add(title).expandY().align(Align.top);
		rootTable.row();
		rootTable.add(winner);
		rootTable.row();
		rootTable.add(exitBtn).expandY().align(Align.bottom);
		
		stage.addActor(rootTable);
	}
}
