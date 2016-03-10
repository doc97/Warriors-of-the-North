package com.tint.wotn.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tint.wotn.Core;
import com.tint.wotn.GameMode;
import com.tint.wotn.ecs.systems.RenderSystem;
import com.tint.wotn.input.BattleInput;
import com.tint.wotn.input.GestureInput;
import com.tint.wotn.input.Inputs;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.utils.CoordinateConversions;

/**
 * {@link Screen} that is used for showing battles
 * @author doc97
 *
 */
public class BattleScreen implements Screen {
	
	private Stage stage;
	private boolean loaded;
	
	public BattleScreen() {
		stage = new Stage(new ExtendViewport(1920, 1080), Core.INSTANCE.batch);
	}
	
	public void load() {
		if(loaded) return;
		loadUI();
		Core.INSTANCE.inputSystem.register(Inputs.BATTLE_SCREEN, new BattleInput(), false);
		Core.INSTANCE.inputSystem.register(Inputs.GESTURE, new GestureInput().detector, false);
		Core.INSTANCE.inputSystem.register(Inputs.BATTLE_SCREEN_UI, stage, false);
		loaded = true;
	}
	
	public void loadUI() {
		Skin skin = new Skin(Gdx.files.internal("skins/default/uiskin.json"));
		
		TextButton endTurn = new TextButton("End turn", skin);
		endTurn.getLabel().setFontScale(2);
		endTurn.pad(10);
		endTurn.center();
		endTurn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Core.INSTANCE.userControlSystem.endTurn();
			}
		});
		
		TextButton menuBtn = new TextButton("Menu", skin);
		menuBtn.getLabel().setFontScale(2);
		menuBtn.pad(10);
		menuBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});

		TextButton settings = new TextButton("Settings", skin);
		settings.getLabel().setFontScale(2);
		settings.pad(10);
		settings.center();
		
		TextButton exit = new TextButton("Exit", skin);
		exit.getLabel().setFontScale(2);
		exit.pad(10);
		exit.center();
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Core.INSTANCE.screenSystem.setScreenToEnter(Screens.CAMPAIGN);
			}
		});
		
		Table topMenu = new Table(skin);
		topMenu.setBackground("default-rect");
		topMenu.add().expandX();
		topMenu.add(endTurn).pad(10);
		
		Table rootUI = new Table(skin);
		rootUI.debug();
		rootUI.setFillParent(true);
		
		rootUI.add(topMenu).fillX();
		rootUI.row();
		rootUI.add().expand();
		
		stage.addActor(rootUI);
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		renderGame();
	}
	
	public void renderGame() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Core.INSTANCE.camera.update();
		Core.INSTANCE.batch.setProjectionMatrix(Core.INSTANCE.camera.orthoCam.combined);
		
		Core.INSTANCE.batch.begin();
		Core.INSTANCE.game.map.render(Core.INSTANCE.batch);
		Core.INSTANCE.ecs.engine.getSystem(RenderSystem.class).render(Core.INSTANCE.batch);
		Core.INSTANCE.batch.end();
		
		stage.act();
		stage.draw();
	}

	public void update(float delta) {
		Core.INSTANCE.screenSystem.update();
		Vector2 screenToWorldCoordinates = CoordinateConversions.screenToWorldPos(
				Gdx.input.getX(),
				Gdx.input.getY());
		Vector2 worldToAxial = CoordinateConversions.worldToAxial(
				Tile.SIZE,
				Tile.SPACING,
				screenToWorldCoordinates.x,
				screenToWorldCoordinates.y);
		Core.INSTANCE.game.map.clearNonPermanentMarkedTiles();
		Core.INSTANCE.game.map.markTile(
					(int) worldToAxial.x,
					(int) worldToAxial.y,
					false);
		
		Core.INSTANCE.actionSystem.update();
	}

	@Override
	public void resize(int width, int height) {
		Core.INSTANCE.camera.resize(width, height);
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void show() {
		load();
		
		if(Gdx.app.getType() == ApplicationType.Desktop) {
			Core.INSTANCE.inputSystem.add(Inputs.BATTLE_SCREEN);
		} else if(Gdx.app.getType() == ApplicationType.Android) {
			Core.INSTANCE.inputSystem.add(Inputs.GESTURE);
		}
		Core.INSTANCE.inputSystem.add(Inputs.BATTLE_SCREEN_UI);
		
		if(Core.INSTANCE.gameMode == GameMode.SINGLEPLAYER)
			Core.INSTANCE.game.startSingleplayerGame();
		
		// Put camera in the centre of the map
		int centerx = Core.INSTANCE.game.map.tiles.length / 2;
		int centery;
		if(centerx == 0) centery = 0;
		else centery = Core.INSTANCE.game.map.tiles[0].length / 2;

		Vector2 centerTile = new Vector2(centerx, centery);
		Vector2 worldCenter = CoordinateConversions.axialToWorld(Tile.SIZE, Tile.SPACING, centerTile);
		Core.INSTANCE.camera.set(worldCenter.x, worldCenter.y);
		
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
	}
	
	@Override
	public void hide() {
		Core.INSTANCE.inputSystem.remove(Inputs.BATTLE_SCREEN);
		Core.INSTANCE.inputSystem.remove(Inputs.GESTURE);
		Core.INSTANCE.inputSystem.remove(Inputs.BATTLE_SCREEN_UI);
	}

	@Override
	public void dispose() {
		
	}
}
