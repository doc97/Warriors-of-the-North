package com.tint.wotn.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tint.wotn.Core;
import com.tint.wotn.UnitType;
import com.tint.wotn.net.constants.Status;
import com.tint.wotn.net.packets.StatusPacket;
import com.tint.wotn.screens.Screens;

public class LobbyScreenUI extends UserInterface {

	public LobbyScreenUI(Skin skin) {
		super(skin);
		stage = new Stage(new ExtendViewport(1920, 1080), Core.INSTANCE.batch);
	}

	@Override
	public void load() {
		stage.clear();
		final Table unitScrollTable = new Table(skin);
		
		final List<Label> unitTypeLabelList = new ArrayList<Label>();
		List<TextButton> unitTypeButtonList = new ArrayList<TextButton>();
		for (UnitType type : UnitType.values()) {
			final Label unitCountLabel = new Label("0", skin);
			final TextButton unitBtn = new TextButton(type.toString(), skin);
			unitBtn.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					int currCount = Integer.parseInt(unitCountLabel.getText().toString());
					unitCountLabel.setText("" + (currCount + 1));
				}
			});
			unitTypeLabelList.add(unitCountLabel);
			unitTypeButtonList.add(unitBtn);
		}
		
		for (Label label : unitTypeLabelList) {
			unitScrollTable.add(label);
		}

		unitScrollTable.row();
		
		for (TextButton btn : unitTypeButtonList) {
			unitScrollTable.add(btn).size(400, 300).pad(50);
		}
		
		ScrollPane unitPane = new ScrollPane(unitScrollTable, skin);
		unitPane.setScrollbarsOnTop(false);
		unitPane.setupFadeScrollBars(0, 0);
		
		final TextButton lobbyStatusBtn = new TextButton("Ready!", skin);
		lobbyStatusBtn.pad(20);
		lobbyStatusBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (lobbyStatusBtn.getText().toString().equals("Cancel")) {
					lobbyStatusBtn.setText("Ready!");
					StatusPacket statusPacket = new StatusPacket();
					statusPacket.status = Status.CLIENT_NOT_READY;
					Core.INSTANCE.multiplayerSystem.client.sendTCP(statusPacket);
				} else if (lobbyStatusBtn.getText().toString().equals("Ready!")) {
					lobbyStatusBtn.setText("Cancel");
					Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
					
					Core.INSTANCE.game.getPlayer().getLoadout().clear();
					Map<UnitType, Integer> loadout = new HashMap<UnitType, Integer>();
					UnitType[] unitTypes = UnitType.values();
					for (int i = 0; i < unitTypeLabelList.size(); i++) {
						Label label = (Label) getElement("Unit Type Label " + i);
						int unitCount = Integer.parseInt(label.getText().toString());
						loadout.put(unitTypes[i], unitCount);
					}
					Core.INSTANCE.game.getPlayer().setLoadout(loadout);
					
					StatusPacket statusPacket = new StatusPacket();
					statusPacket.status = Status.CLIENT_READY;
					Core.INSTANCE.multiplayerSystem.client.sendTCP(statusPacket);
				}
			}
		});
		
		TextButton backBtn = new TextButton("Back", skin);
		backBtn.pad(20);
		backBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Core.INSTANCE.screenSystem.setScreenToEnter(Screens.MULTIPLAYER);
			}
		});
		
		Table container = new Table(skin);
		container.debug();
		container.setFillParent(true);
		container.add().expand().fill().pad(10).colspan(3);
		container.row();
		container.add(unitPane).expandX().pad(10).colspan(3);
		container.row();
		container.add().expand().fill().pad(10).colspan(3);
		container.row();
		container.add(backBtn).align(Align.bottomLeft).pad(10);
		container.add().expandX();
		container.add(lobbyStatusBtn).align(Align.bottomRight).pad(10);
		
		stage.addActor(container);
		
		for (int i = 0; i < unitTypeLabelList.size(); i++) {
			mapElement("Unit Type Label " + i, unitTypeLabelList.get(i));
		}
	}
}
