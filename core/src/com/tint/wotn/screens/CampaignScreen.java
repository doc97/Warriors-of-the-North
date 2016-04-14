package com.tint.wotn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.tint.wotn.Core;
import com.tint.wotn.input.CampaignInput;
import com.tint.wotn.input.Inputs;
import com.tint.wotn.ui.UserInterfaces;

/**
 * {@link Screen} that functions as a mission screen
 * @author doc97
 *
 */
public class CampaignScreen implements Screen {

	private AtlasRegion map;
	private boolean loaded;
	
	public void load() {
		if(loaded) return;
		loaded = true;
		Core.INSTANCE.inputSystem.register(Inputs.CAMPAIGN_SCREEN, new CampaignInput(), false);
		Core.INSTANCE.inputSystem.register(Inputs.CAMPAIGN_SCREEN_UI,
				Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.CAMPAIGN_SCREEN_UI).getStage(), false);
	}
	
	@Override
	public void show() {
		load();
		Core.INSTANCE.inputSystem.add(Inputs.CAMPAIGN_SCREEN);
		Core.INSTANCE.inputSystem.add(Inputs.CAMPAIGN_SCREEN_UI);
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Core.INSTANCE.screenSystem.update();
		
		Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.CAMPAIGN_SCREEN_UI).getStage().act();
		Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.CAMPAIGN_SCREEN_UI).getStage().draw();
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
