package com.tint.wotn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tint.wotn.Core;
import com.tint.wotn.input.CampaignScreenInput;
import com.tint.wotn.input.Inputs;
import com.tint.wotn.input.QuestInput;
import com.tint.wotn.ui.UserInterfaces;

/**
 * {@link Screen} that functions as a mission screen
 * @author doc97
 *
 */
public class CampaignScreen implements Screen {

	private boolean loaded;
	
	public void load() {
		if(loaded) return;
		loaded = true;
		Core.INSTANCE.inputSystem.register(Inputs.CAMPAIGN_SCREEN, new CampaignScreenInput(), false);
		Core.INSTANCE.inputSystem.register(Inputs.CAMPAIGN_SCREEN_UI,
				Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.CAMPAIGN_SCREEN_UI).getStage(), false);
		Core.INSTANCE.inputSystem.register(Inputs.QUEST_BUTTONS, new QuestInput(), false);
	}
	
	@Override
	public void show() {
		load();
		Core.INSTANCE.inputSystem.add(Inputs.CAMPAIGN_SCREEN);
		Core.INSTANCE.inputSystem.add(Inputs.CAMPAIGN_SCREEN_UI);
		Core.INSTANCE.inputSystem.add(Inputs.QUEST_BUTTONS);
		
		Stage stage = Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.CAMPAIGN_SCREEN_UI).getStage();
		Core.INSTANCE.UISystem.setStage(stage);

		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.CAMPAIGN_SCREEN_UI).update(delta);
		Core.INSTANCE.update(delta);
		
		Core.INSTANCE.camera.update();
		Core.INSTANCE.batch.setProjectionMatrix(Core.INSTANCE.camera.getOrthoCam().combined);
		
		Core.INSTANCE.batch.begin();
		Core.INSTANCE.world.render(Core.INSTANCE.batch);
		Core.INSTANCE.batch.end();
		
		Core.INSTANCE.UISystem.draw();
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
		Core.INSTANCE.inputSystem.remove(Inputs.CAMPAIGN_SCREEN);
		Core.INSTANCE.inputSystem.remove(Inputs.CAMPAIGN_SCREEN_UI);
		Core.INSTANCE.inputSystem.remove(Inputs.QUEST_BUTTONS);
		Core.INSTANCE.UISystem.setStage(null);
	}

	@Override
	public void dispose() {
		Core.INSTANCE.inputSystem.unregister(Inputs.CAMPAIGN_SCREEN);
		Core.INSTANCE.inputSystem.unregister(Inputs.CAMPAIGN_SCREEN_UI);
		Core.INSTANCE.inputSystem.unregister(Inputs.QUEST_BUTTONS);
	}
}
