package com.tint.wotn.ui;

import java.util.HashMap;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tint.wotn.Core;
import com.tint.wotn.screens.Screens;

public class BattleScreenUI extends UserInterface {
	
	private final Label unitHealth;
	private final Label unitAttack;

	public BattleScreenUI(Skin skin) {
		super(skin);
		stage = new Stage(new ExtendViewport(1920, 1080), Core.INSTANCE.batch);
		unitHealth = new Label("", skin);
		unitAttack = new Label("", skin);
	}

	@Override
	public void load() {
		stage.clear();
		
		TextButton endTurnBtn = new TextButton("End turn", skin);
		endTurnBtn.getLabel().setFontScale(2);
		endTurnBtn.pad(10);
		endTurnBtn.center();
		endTurnBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
				Core.INSTANCE.ucc.endTurn();
			}
		});
		
		TextButton menuBtn = new TextButton("Menu", skin);
		menuBtn.getLabel().setFontScale(2);
		menuBtn.pad(10);
		menuBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
				Core.INSTANCE.screenSystem.setScreenToEnter(Screens.MAIN_MENU);
			}
		});
		
		Table topMenu = new Table(skin);
		topMenu.setBackground("default-rect");
		topMenu.add(unitHealth).pad(10);
		topMenu.add(unitAttack).pad(10);
		topMenu.add().expandX();
		topMenu.add(menuBtn).pad(10);
		topMenu.add(endTurnBtn).pad(10);
		
		Table rootTable = new Table(skin);
		rootTable.setFillParent(true);
		
		rootTable.add(topMenu).fillX();
		rootTable.row();
		rootTable.add().expand();
		
		stage.addActor(rootTable);
		
		getStorage().addDataset("Data", new HashMap<String, String>());
		getStorage().storeData("Data", "Health", "");
		getStorage().storeData("Data", "Attack", "");
	}
	
	@Override
	public void update(float delta) {
		unitHealth.setText(getStorage().getData("Data", "Health"));
		unitAttack.setText(getStorage().getData("Data", "Attack"));
	}
}
