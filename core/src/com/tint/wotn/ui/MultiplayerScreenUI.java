package com.tint.wotn.ui;

import java.io.IOException;

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
import com.tint.wotn.net.Player;
import com.tint.wotn.screens.Screens;

public class MultiplayerScreenUI extends UserInterface {

	private boolean multiplayerChoiceScreen;

	public MultiplayerScreenUI(Skin skin) {
		super(skin);
		stage = new Stage(new ExtendViewport(1920, 1080), Core.INSTANCE.batch);
	}
	
	@Override
	public void load() {
		// Widgets
		final Table table = new Table();
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
		
		final TextButton connectBtn = new TextButton("Connect", skin);
		connectBtn.getLabel().setFontScale(2);
		connectBtn.pad(10);
		connectBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
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
		backBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				back();
			}
		});
		
		final TextButton hostBtn = new TextButton("Host Game", skin);
		hostBtn.getLabel().setFontScale(4);
		hostBtn.pad(10);
		hostBtn.getLabelCell().expand();
		hostBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		
		final TextButton joinBtn = new TextButton("Join Game", skin);
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
		
		table.add(joinBtn).pad(100).expand().fill();
		table.add(hostBtn).pad(100).expand().fill();
		table.row();
		table.add();
		table.add(backBtn).align(Align.bottomRight).pad(10, 20, 10, 20);
		stage.clear();		// For some reason stage is not empty
		stage.addActor(table);
		System.out.println(stage.getActors().size);

		mapElement("Table", table);
		mapElement("Join text button", joinBtn);
		mapElement("Host text button", hostBtn);
		mapElement("Back text button", backBtn);
	}
	
	public void back() {
		Table table = (Table) getElement("Table");
		table.clear();
		table.add((TextButton) getElement("Join text button")).pad(100).expand().fill();
		table.add((TextButton) getElement("Host text button")).pad(100).expand().fill();
		table.row();
		table.add();
		table.add((TextButton) getElement("Back text button")).align(Align.bottomRight).pad(10, 20, 10, 20);

		if(multiplayerChoiceScreen) {
			Core.INSTANCE.screenSystem.setScreenToEnter(Screens.MAIN_MENU);
		}

		multiplayerChoiceScreen = true;
	}

}
