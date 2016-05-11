package com.tint.wotn.ui;

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
	
	public BattleScreenUI(Skin skin) {
		super(skin);
		stage = new Stage(new ExtendViewport(1920, 1080), Core.INSTANCE.batch);
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
				Core.INSTANCE.userControlSystem.endTurn();
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
		
		Label unitHealth = new Label("", skin);
		Label unitAttack = new Label("", skin);

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
		
		mapElement("Unit health label", unitHealth);
		mapElement("Unit attack label", unitAttack);
	}
}
