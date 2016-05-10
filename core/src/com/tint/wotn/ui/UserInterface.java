package com.tint.wotn.ui;

import java.util.HashMap;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class UserInterface {
	protected Stage stage;
	protected Skin skin;
	private HashMap<String, Actor> elements = new HashMap<String, Actor>();
	private UIDataStorage storage = new UIDataStorage();
	
	public UserInterface(Skin skin) {
		this.skin = skin;
	}
	
	public abstract void load();
	
	public void render() {
		update();
		draw();
	}
	
	public void update() {
		stage.act();
	}
	
	public void draw() {
		stage.draw();
	}
	
	public void mapElement(String key, Actor actor) {
		elements.put(key, actor);
	}
	
	public Actor getElement(String key) {
		return elements.get(key);
	}
	
	public UIDataStorage getStorage() {
		return storage;
	}
	
	public Stage getStage() {
		return stage;
	}
}
