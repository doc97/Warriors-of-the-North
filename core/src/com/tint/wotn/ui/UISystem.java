package com.tint.wotn.ui;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UISystem {

	private HashMap<UserInterfaces, UserInterface> interfaces = new HashMap<UserInterfaces, UserInterface>();
	
	public void initialize() {
		Skin skin = new Skin(Gdx.files.internal("skins/default/uiskin.json"));
		addUserInterface(UserInterfaces.BATTLE_SCREEN_UI, new BattleScreenUI(skin));
		addUserInterface(UserInterfaces.CAMPAIGN_SCREEN_UI, new CampaignScreenUI(skin));
		addUserInterface(UserInterfaces.MAIN_MENU_SCREEN_UI, new MainMenuScreenUI(skin));
		addUserInterface(UserInterfaces.MULTIPLAYER_SCREEN_UI, new MultiplayerScreenUI(skin));
		addUserInterface(UserInterfaces.LOBBY_SCREEN_UI, new LobbyScreenUI(skin));
		addUserInterface(UserInterfaces.END_SCREEN_UI, new EndScreenUI(skin));
	}
	
	public void load() {
		for (UserInterface ui : interfaces.values()) {
			ui.load();
		}
	}
	
	public void addUserInterface(UserInterfaces key, UserInterface ui) {
		interfaces.put(key, ui);
	}
	
	public UserInterface getUserInterface(UserInterfaces key) {
		return interfaces.get(key);
	}
}
