package com.tint.wotn.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tint.wotn.Core;
import com.tint.wotn.GameMode;
import com.tint.wotn.ecs.systems.EffectSystem;
import com.tint.wotn.ecs.systems.RenderSystem;
import com.tint.wotn.input.BattleScreenInput;
import com.tint.wotn.input.GestureInput;
import com.tint.wotn.input.Inputs;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.ui.UserInterfaces;
import com.tint.wotn.utils.CoordinateConversions;

/**
 * {@link Screen} that is used for showing battles
 * @author doc97
 *
 */
public class BattleScreen implements Screen {
	
	private boolean loaded;
	
	public void load() {
		if(loaded) return;
		Core.INSTANCE.inputSystem.register(Inputs.BATTLE_SCREEN, new BattleScreenInput(), false);
		Core.INSTANCE.inputSystem.register(Inputs.GESTURE, new GestureInput().detector, false);
		Core.INSTANCE.inputSystem.register(Inputs.BATTLE_SCREEN_UI, 
				Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.BATTLE_SCREEN_UI).getStage(), false);
		loaded = true;
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		renderGame();
	}
	
	public void renderGame() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Core.INSTANCE.camera.update();
		Core.INSTANCE.batch.setProjectionMatrix(Core.INSTANCE.camera.getOrthoCam().combined);
		
		Core.INSTANCE.batch.begin();
		Core.INSTANCE.game.getMap().render(Core.INSTANCE.batch);
		Core.INSTANCE.ecs.engine.getSystem(RenderSystem.class).render(Core.INSTANCE.batch);
		Core.INSTANCE.batch.end();
		
		Core.INSTANCE.UISystem.draw();
	}

	public void update(float delta) {
		Vector2 screenToWorldCoordinates = CoordinateConversions.screenToWorldPos(
				Gdx.input.getX(),
				Gdx.input.getY());
		Vector2 worldToAxial = CoordinateConversions.worldToAxial(
				Tile.SIZE,
				Tile.SPACING,
				screenToWorldCoordinates.x,
				screenToWorldCoordinates.y);
		Core.INSTANCE.game.getMap().clearNonPermanentMarkedTiles();
		Core.INSTANCE.game.getMap().markTile(
					(int) worldToAxial.x,
					(int) worldToAxial.y,
					false);
		
		Core.INSTANCE.actionSystem.update();
		Core.INSTANCE.ecs.engine.getSystem(EffectSystem.class).update();
		Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.BATTLE_SCREEN_UI).update(delta);
		Core.INSTANCE.update(delta);
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

		Stage stage = Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.BATTLE_SCREEN_UI).getStage();
		Core.INSTANCE.UISystem.setStage(stage);
		
		Core.INSTANCE.audioSystem.stopAllMusic();
		
		if(Core.INSTANCE.gameMode == GameMode.SINGLEPLAYER)
			Core.INSTANCE.game.startBattle();
		
		// Put camera in the centre of the map
		int centerx = Core.INSTANCE.game.getMap().tiles.length / 2;
		int centery;
		if(centerx == 0) centery = 0;
		else centery = Core.INSTANCE.game.getMap().tiles[0].length / 2;

		Vector2 centerTile = new Vector2(centerx, centery);
		Vector2 worldCenter = CoordinateConversions.axialToWorld(Tile.SIZE, Tile.SPACING, centerTile);
		Core.INSTANCE.camera.set(worldCenter.x, worldCenter.y);
		
		Gdx.gl.glClearColor(0.82f, 0.86f, 0.9f, 1.0f);
	}
	
	@Override
	public void hide() {
		Core.INSTANCE.inputSystem.remove(Inputs.BATTLE_SCREEN);
		Core.INSTANCE.inputSystem.remove(Inputs.GESTURE);
		Core.INSTANCE.inputSystem.remove(Inputs.BATTLE_SCREEN_UI);
		Core.INSTANCE.UISystem.setStage(null);
	}

	@Override
	public void dispose() {
		Core.INSTANCE.inputSystem.unregister(Inputs.BATTLE_SCREEN);
		Core.INSTANCE.inputSystem.unregister(Inputs.BATTLE_SCREEN_UI);
		Core.INSTANCE.inputSystem.unregister(Inputs.GESTURE);
	}
}
