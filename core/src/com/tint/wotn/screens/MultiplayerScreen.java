package com.tint.wotn.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tint.wotn.Core;
import com.tint.wotn.input.Inputs;
import com.tint.wotn.input.MultiplayerScreenInput;
import com.tint.wotn.net.Player;

/**
 * {@link Screen} that is used for configuring multiplayer
 * @author doc97
 *
 */
public class MultiplayerScreen implements Screen {
	private Stage stage;
	private Table table;
	private TextButton joinBtn;
	private TextButton hostBtn;
	private TextButton connectBtn;
	private TextButton backBtn;
	private boolean multiplayerChoiceScreen = true;
	private boolean loaded;

	public void load() {
		if(loaded) return;
		stage = new Stage(new ExtendViewport(1920, 1080), Core.INSTANCE.batch);
		loadUI();
		Core.INSTANCE.inputSystem.register(Inputs.MULTIPLAYER_SCREEN_UI, stage, false);

		MultiplayerScreenInput multiplayerScreenInput = new MultiplayerScreenInput(this);
		Core.INSTANCE.inputSystem.register(Inputs.MULTIPLAYER_SCREEN, multiplayerScreenInput, false);
		loaded = true;
	}
	
	public void loadUI() {
		Skin skin = new Skin(Gdx.files.internal("skins/default/uiskin.json"));
		
		// Widgets
		table = new Table();
		table.debug();
		table.setFillParent(true);
		
		final Label ipLabel = new Label("IP: ", skin);
		ipLabel.setFontScale(2);
		final Label portLabel = new Label("Port: ", skin);
		portLabel.setFontScale(2);
		
		final TextField ipField = new TextField("localhost", skin);
		ipField.getStyle().font.getData().setScale(2);
		final TextField portField = new TextField("6666", skin);
		portField.getStyle().font.getData().setScale(2);
		
		connectBtn = new TextButton("Connect", skin);
		connectBtn.getLabel().setFontScale(2);
		connectBtn.pad(10);
		connectBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					Player player = new Player();
					player.name = "Bob";
					Core.INSTANCE.multiplayerSystem.connect(player, ipField.getText(),
							Integer.parseInt(portField.getText()));
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Failed to connect to server!");
				}
			}
		});

		backBtn = new TextButton("Back", skin);
		backBtn.getLabel().setFontScale(2);
		backBtn.pad(10);
		backBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				back();
			}
		});
		
		hostBtn = new TextButton("Host Game", skin);
		hostBtn.getLabel().setFontScale(4);
		hostBtn.pad(10);
		hostBtn.getLabelCell().expand();
		hostBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		
		joinBtn = new TextButton("Join Game", skin);
		joinBtn.getLabel().setFontScale(4);
		joinBtn.pad(10);
		joinBtn.getLabelCell().expand().fill();
		joinBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				table.clear();
				table.add(ipLabel).fill();
				table.add(ipField).fill();
				table.row();
				table.add(portLabel).fill();
				table.add(portField).fill();
				table.row();
				table.add(backBtn);
				table.add(connectBtn).fill();

				multiplayerChoiceScreen = false;
			}
		});
		
		table.add(joinBtn).pad(50, 100, 150, 100).expand().fill();
		table.add(hostBtn).pad(50, 100, 150, 100).expand().fill();
		table.row();
		table.add();
		table.add(backBtn).align(Align.bottomRight).pad(10, 20, 10, 20);
		stage.addActor(table);
	}

	@Override
	public void show() {
		load();
		multiplayerChoiceScreen = true;
		Core.INSTANCE.inputSystem.add(Inputs.MULTIPLAYER_SCREEN);
		Core.INSTANCE.inputSystem.add(Inputs.MULTIPLAYER_SCREEN_UI);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Core.INSTANCE.screenSystem.update();
		stage.act();
		stage.draw();
	}
	
	public void back() {
		table.clear();
		if(!multiplayerChoiceScreen) {
			table.add(joinBtn).pad(100).expand().fill();
			table.add(hostBtn).pad(100).expand().fill();
			table.row();
			table.add();
			table.add(backBtn).align(Align.bottomRight).pad(10, 20, 10, 20);
			multiplayerChoiceScreen = true;
		} else {
			Core.INSTANCE.screenSystem.setScreenToEnter(Screens.MAIN_MENU);
		}
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
		stage.clear();
		Core.INSTANCE.inputSystem.remove(Inputs.MULTIPLAYER_SCREEN);
		Core.INSTANCE.inputSystem.remove(Inputs.MAIN_MENU_SCREEN_UI);
	}

	@Override
	public void dispose() {
		
	}
}
