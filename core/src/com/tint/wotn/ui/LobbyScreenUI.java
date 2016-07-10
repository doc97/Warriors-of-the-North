package com.tint.wotn.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
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
		final Container<Actor> loadoutScrollContainer = new Container<Actor>();
		
		final HorizontalGroup loadoutScrollGroup = new HorizontalGroup();
		loadoutScrollGroup.space(20);
		
		final Label emptyLabel = new Label("<Empty>", skin);
		
		// Fill unit lists
		List<TextButton> unitTypeButtonList = new ArrayList<TextButton>();
		for (final UnitType type : UnitType.values()) {
			final TextButton unitBtn = new TextButton(type.toString(), skin);
			unitTypeButtonList.add(unitBtn);
			unitBtn.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					String readyStatus = getStorage().getData("Data", "Ready");
					if (readyStatus.equals("") || readyStatus.equals("true")) {
						return;
					}
					
					final TextButton btn = new TextButton(unitBtn.getText().toString(), skin);
					btn.addListener(new ClickListener() {
						@Override
						public void clicked(InputEvent ev, float x, float y) {
							String readyStatus = getStorage().getData("Data", "Ready");
							if (readyStatus.equals("") || readyStatus.equals("true")) return;
							
							loadoutScrollGroup.removeActor(btn);
							if (loadoutScrollGroup.getChildren().size == 0)
								loadoutScrollContainer.setActor(emptyLabel);
							
							String currData = getStorage().getData("Loadout", btn.getText().toString());
							if (currData.equals(""))
								getStorage().storeData("Loadout", btn.getText().toString(), "0");
							else
								getStorage().storeData("Loadout", btn.getText().toString(),
										String.valueOf(Integer.parseInt(currData) - 1));
						}
					});

					// Add button to list after a button with same text
					// if possible
					boolean btnAdded = false;
					for (Actor actor : loadoutScrollGroup.getChildren()) {
						if (actor instanceof TextButton) {
							if (((TextButton) actor).getText().equals(btn.getText())) {
								loadoutScrollGroup.addActorAfter(actor, btn);
								btnAdded = true;
								break;
							}
						}
					}
					
					if (!btnAdded)
						loadoutScrollGroup.addActor(btn);
					
					loadoutScrollContainer.setActor(loadoutScrollGroup);

					String currData = getStorage().getData("Loadout", unitBtn.getText().toString());
					if (currData.equals(""))
						getStorage().storeData("Loadout", unitBtn.getText().toString(), "1");
					else
						getStorage().storeData("Loadout", unitBtn.getText().toString(),
								String.valueOf(Integer.parseInt(currData) + 1));
				}
			});
		}
		
		// Add buttons to scroll table
		for (TextButton btn : unitTypeButtonList) {
			unitScrollTable.add(btn).size(400, 300).pad(50);
		}
		
		loadoutScrollContainer.setActor(emptyLabel);
		
		ScrollPane unitPane = new ScrollPane(unitScrollTable, skin);
		unitPane.setScrollingDisabled(false, true);
		unitPane.setupFadeScrollBars(0, 0);
		unitPane.setOverscroll(true, false);
		
		ScrollPane loadoutPane = new ScrollPane(loadoutScrollContainer, skin);
		loadoutPane.setScrollingDisabled(false, true);
		loadoutPane.setupFadeScrollBars(0, 0);
		loadoutPane.setOverscroll(true, false);

		
		final TextButton lobbyStatusBtn = new TextButton("Ready!", skin);
		lobbyStatusBtn.pad(20);
		lobbyStatusBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
				String readyStatus = getStorage().getData("Data", "Ready");
				if (readyStatus.equals("")) readyStatus = "true";
				
				if (readyStatus.equals("true")) {
					lobbyStatusBtn.setText("Ready!");
					getStorage().storeData("Data", "Ready", "false");
					
					StatusPacket statusPacket = new StatusPacket();
					statusPacket.status = Status.CLIENT_NOT_READY;
					Core.INSTANCE.multiplayerSystem.client.sendTCP(statusPacket);
				} else if (readyStatus.equals("false")) {
					lobbyStatusBtn.setText("Cancel");
					getStorage().storeData("Data", "Ready", "true");
					Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
					
					Core.INSTANCE.game.getPlayer().getLoadout().clear();
					Map<UnitType, Integer> loadout = new HashMap<UnitType, Integer>();
					for (String unitType : getStorage().getDataset("Loadout").keySet()) {
						String value = getStorage().getData("Loadout", unitType);
						loadout.put(Enum.valueOf(UnitType.class, unitType), Integer.parseInt(value));
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
		backBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
				Core.INSTANCE.screenSystem.setScreenToEnter(Screens.MULTIPLAYER);
				Core.INSTANCE.multiplayerSystem.disconnect();
			}
		});
		
		Table container = new Table(skin);
		container.setFillParent(true);
		container.add(loadoutPane).expand().pad(10).colspan(3);
		container.row();
		container.add(unitPane).expandX().pad(10).colspan(3);
		container.row();
		container.add().expand().fill().pad(10).colspan(3);
		container.row();
		container.add(backBtn).align(Align.bottomLeft).pad(10);
		container.add().expandX();
		container.add(lobbyStatusBtn).align(Align.bottomRight).pad(10);
		
		stage.addActor(container);
		
		
		// Creating storage space
		getStorage().addDataset("Loadout", new HashMap<String, String>());
		getStorage().addDataset("Data", new HashMap<String, String>());
		getStorage().storeData("Data", "Ready", "false");
	}
}
