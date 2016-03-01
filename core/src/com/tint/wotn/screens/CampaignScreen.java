package com.tint.wotn.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.tint.wotn.Core;
import com.tint.wotn.input.CampaignInput;
import com.tint.wotn.input.Inputs;
import com.tint.wotn.missions.Mission;

/**
 * {@link Screen} that functions as a mission screen
 * @author doc97
 *
 */
public class CampaignScreen implements Screen {

	private AtlasRegion map;
	private Stage stage;
	private Table missionBriefingTable;
	private Table baseUITable;
	private List<TextButton> missionBtns = new ArrayList<TextButton>();
	private int currentMissionID;
	private boolean loaded;
	
	public CampaignScreen() {
		stage = new Stage(Core.INSTANCE.camera.viewport);
	}
	
	public void load() {
		if(loaded) return;
		loaded = true;
		loadUI();
		Core.INSTANCE.inputSystem.register(Inputs.CAMPAIGN_SCREEN_UI, stage, true);
		Core.INSTANCE.inputSystem.register(Inputs.CAMPAIGN_SCREEN, new CampaignInput(), false);
	}
	
	public void loadUI() {
		Skin skin = new Skin(Gdx.files.internal("skins/default/uiskin.json"));
		
		missionBriefingTable = new Table();
		missionBriefingTable.debug();
		missionBriefingTable.setFillParent(true);
		missionBriefingTable.pad(30);
		missionBriefingTable.setVisible(false);

		baseUITable = new Table();
		baseUITable.debug();
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
				Core.INSTANCE.screenSystem.enterScreen(Screens.MAIN_MENU);
			}
		});

		TextButton playBtn = new TextButton("Play", skin);
		playBtn.getLabel().setFontScale(2);
		playBtn.pad(10);
		playBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(currentMissionID >= 0) {
					Core.INSTANCE.levelSystem.enterLevel(currentMissionID);
					Core.INSTANCE.screenSystem.enterScreen(Screens.BATTLE);
				}
			}
		});
		
		TextButton cancelBtn = new TextButton("Cancel", skin);
		cancelBtn.getLabel().setFontScale(2);
		cancelBtn.pad(10);
		cancelBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				missionBriefingTable.setVisible(false);
				currentMissionID = -1;
			}
		});
		
		List<Mission> missions = Core.INSTANCE.missionSystem.getAvailableMissions();
		for(final Mission mission : missions) {
			TextButton missionBtn = new TextButton(mission.name, skin);
			missionBtn.setPosition(mission.position.x, mission.position.y);
			missionBtn.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					nameLabel.setText(mission.name);
					legendLabel.setText(mission.legend);
					missionBriefingTable.setVisible(true);
					currentMissionID = mission.ID;
				}
			});
			missionBtns.add(missionBtn);
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
		for(TextButton textBtn : missionBtns)
			stage.addActor(textBtn);
		stage.addActor(missionBriefingTable);
		stage.addActor(baseUITable);
	}
	
	@Override
	public void show() {
		load();
		Core.INSTANCE.inputSystem.add(Inputs.CAMPAIGN_SCREEN);
		Core.INSTANCE.inputSystem.add(Inputs.CAMPAIGN_SCREEN_UI);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Core.INSTANCE.screenSystem.update();
		
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
		Core.INSTANCE.inputSystem.remove(Inputs.CAMPAIGN_SCREEN);
		Core.INSTANCE.inputSystem.remove(Inputs.CAMPAIGN_SCREEN_UI);
	}

	@Override
	public void dispose() {
		
	}
}
