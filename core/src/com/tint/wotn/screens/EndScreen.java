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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tint.wotn.Core;
import com.tint.wotn.input.Inputs;

public class EndScreen implements Screen {

	private Stage stage;
	private boolean loaded;
	
	public EndScreen(){
		stage = new Stage(new ExtendViewport(1920, 1080), Core.INSTANCE.batch);
	}
	
	public void load() {
		if(loaded) return;
		loaded = true;
		loadUI();
		Core.INSTANCE.inputSystem.register(Inputs.END_SCREEN_UI, stage, false);
	}
	
	public void loadUI() {
		Skin skin = new Skin(Gdx.files.internal("skins/default/uiskin.json"));
		Label title = new Label("END SCREEN", skin);
		title.setFontScale(10);
		
		Label winner = new Label("Winner: " + "user dummy", skin);
		winner.setFontScale(5);
		
		TextButton exitBtn = new TextButton("Ok... ;C", skin);
		exitBtn.getLabel().setFontScale(4);
		exitBtn.pad(10);
		exitBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
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
	
	@Override
	public void show() {
		load();
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		Core.INSTANCE.inputSystem.add(Inputs.END_SCREEN_UI);
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
		Core.INSTANCE.inputSystem.remove(Inputs.END_SCREEN_UI);
	}

	@Override
	public void dispose() {
		
	}
}
