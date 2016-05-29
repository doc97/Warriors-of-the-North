package com.tint.wotn.ui;

import java.io.IOException;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tint.wotn.Core;
import com.tint.wotn.net.Player;
import com.tint.wotn.screens.Screens;

public class MultiplayerScreenUI extends UserInterface {

	private final Table rootTable;
	private final Table modeTable;
	private final Table joinTable;
	
	public MultiplayerScreenUI(Skin skin) {
		super(skin);
		stage = new Stage(new ExtendViewport(1920, 1080), Core.INSTANCE.batch);
		rootTable = new Table(skin);
		modeTable = new Table(skin);
		joinTable = new Table(skin);
	}
	
	@Override
	public void load() {
		stage.clear();

		final Label ipLabel = new Label("IP: ", skin);
		ipLabel.setFontScale(2);
		final Label portLabel = new Label("Port: ", skin);
		portLabel.setFontScale(2);
		
		final TextField ipField = new TextField("localhost", skin);
		ipField.getStyle().font.getData().setScale(2);
		final TextField portField = new TextField("6666", skin);
		portField.getStyle().font.getData().setScale(2);
		
		final TextButton connectBtn = new TextButton("Connect", skin);
		connectBtn.getLabel().setFontScale(2);
		connectBtn.pad(10);
		connectBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
				try {
					Player player = new Player();
					player.setName("Bob");
					Core.INSTANCE.multiplayerSystem.connect(player, ipField.getText(),
							Integer.parseInt(portField.getText()));
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Failed to connect to server!");
				}
			}
		});

		final TextButton backBtn = new TextButton("Back", skin);
		backBtn.getLabel().setFontScale(2);
		backBtn.pad(10);
		backBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
				back();
			}
		});
		
		final TextButton hostBtn = new TextButton("Host Game", skin);
		hostBtn.getLabel().setFontScale(4);
		hostBtn.pad(10);
		hostBtn.getLabelCell().expand();
		hostBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
			}
		});
		
		final TextButton joinBtn = new TextButton("Join Game", skin);
		joinBtn.getLabel().setFontScale(4);
		joinBtn.pad(10);
		joinBtn.getLabelCell().expand().fill();
		joinBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
				modeTable.remove();
				rootTable.add(joinTable);
			}
		});
		
		joinTable.add(ipLabel).fill();
		joinTable.add(ipField).fill();
		joinTable.row();
		joinTable.add(portLabel).fill();
		joinTable.add(portField).fill();
		joinTable.row();
		joinTable.add(backBtn);
		joinTable.add(connectBtn).fill();

		modeTable.add(joinBtn).pad(100).expand().fill();
		modeTable.add(hostBtn).pad(100).expand().fill();
		modeTable.row();
		modeTable.add();
		modeTable.add(backBtn).align(Align.bottomRight).pad(10, 20, 10, 20);
		
		rootTable.setFillParent(true);
		rootTable.add(modeTable);
		
		stage.addActor(rootTable);
	}

	public void back() {
		boolean modePage = modeTable.hasParent();
		modeTable.remove();
		joinTable.remove();
		rootTable.add(modeTable);
		
		if(modePage) {
			Core.INSTANCE.screenSystem.setScreenToEnter(Screens.MAIN_MENU);
		}
	}
}
