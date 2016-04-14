package com.tint.wotn.ui;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tint.wotn.Core;
import com.tint.wotn.missions.Mission;
import com.tint.wotn.screens.Screens;

public class CampaignScreenUI extends UserInterface {

	public CampaignScreenUI(Skin skin) {
		super(skin);
		stage = new Stage(new ExtendViewport(1920, 1080), Core.INSTANCE.batch);
	}

	@Override
	public void load() {
		Skin skin = new Skin(Gdx.files.internal("skins/default/uiskin.json"));
		
		final Table missionBriefingTable = new Table();
		missionBriefingTable.setFillParent(true);
		missionBriefingTable.pad(30);
		missionBriefingTable.setVisible(false);

		Table baseUITable = new Table();
		baseUITable.setFillParent(true);
		baseUITable.pad(30);

		final Label legendLabel = new Label("", skin);
		legendLabel.setAlignment(Align.center);
		
		final Label nameLabel = new Label("", skin);
		nameLabel.setFontScale(3);
		nameLabel.setAlignment(Align.center);
		
		TextButton backBtn = new TextButton("Back", skin);
		backBtn.getLabel().setFontScale(2);
		backBtn.pad(10);
		backBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Core.INSTANCE.screenSystem.setScreenToEnter(Screens.MAIN_MENU);
			}
		});

		final TextButton playBtn = new TextButton("Play", skin);
		playBtn.getLabel().setFontScale(2);
		playBtn.pad(10);
		playBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Core.INSTANCE.levelSystem.enterCurrentLevel();
				Core.INSTANCE.screenSystem.setScreenToEnter(Screens.BATTLE);
			}
		});
		
		TextButton cancelBtn = new TextButton("Cancel", skin);
		cancelBtn.getLabel().setFontScale(2);
		cancelBtn.pad(10);
		cancelBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				missionBriefingTable.setVisible(false);
			}
		});
		
		// Create mission buttons
		List<Mission> availableMissions = Core.INSTANCE.missionSystem.getAvailableMissions();
		for(final Mission mission : availableMissions) {
			final TextButton missionBtn = new TextButton(mission.name, skin);
			missionBtn.setPosition(mission.position.x, mission.position.y);
			missionBtn.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					nameLabel.setText(mission.name);
					legendLabel.setText(mission.legend);
					missionBriefingTable.setVisible(true);
					Core.INSTANCE.levelSystem.setCurrentLevel(mission.ID);
				}
			});
			stage.addActor(missionBtn);
		}
		
		// Add to table
		missionBriefingTable.add(nameLabel).expandX().pad(10).colspan(2);
		missionBriefingTable.row();
		missionBriefingTable.add(legendLabel).expand().fill().pad(10, 200, 10, 200).colspan(2);
		missionBriefingTable.row();
		missionBriefingTable.add(playBtn).pad(0, 0, playBtn.getHeight() * 3, 0).expandX();
		missionBriefingTable.add(cancelBtn).pad(0, 0, cancelBtn.getHeight() * 3, 0).expandX();
		missionBriefingTable.row();
		missionBriefingTable.add().bottom();
		
		baseUITable.add(backBtn).expand().align(Align.bottomRight);
		
		// Add to stage
		stage.addActor(missionBriefingTable);
		stage.addActor(baseUITable);
	}

}
