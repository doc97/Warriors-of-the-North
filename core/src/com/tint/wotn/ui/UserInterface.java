package com.tint.wotn.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class UserInterface {
	protected Stage stage;
	protected Skin skin;
	private UIDataStorage storage = new UIDataStorage();
	
	public UserInterface(Skin skin) {
		this.skin = skin;
	}
	
	public abstract void load();
	public void update(float delta) {}
	
	public UIDataStorage getStorage() {
		return storage;
	}
	
	public Stage getStage() {
		return stage;
	}
}
