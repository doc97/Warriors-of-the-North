package com.tint.wotn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.GameMode;
import com.tint.wotn.ecs.systems.RenderSystem;
import com.tint.wotn.input.Inputs;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.utils.CoordinateConversions;

public class GameScreen implements Screen {
	
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
		
		// Debug
		Core.INSTANCE.camera.update();
		Core.INSTANCE.shapeRenderer.setProjectionMatrix(Core.INSTANCE.camera.orthoCam.combined);
		
		Core.INSTANCE.shapeRenderer.begin(ShapeType.Line);
		Core.INSTANCE.shapeRenderer.line(
				Core.INSTANCE.camera.orthoCam.position.x,
				Core.INSTANCE.camera.orthoCam.position.y - Core.INSTANCE.camera.orthoCam.viewportHeight / 2,
				Core.INSTANCE.camera.orthoCam.position.x,
				Core.INSTANCE.camera.orthoCam.position.y + Core.INSTANCE.camera.orthoCam.viewportHeight / 2
				);
		Core.INSTANCE.shapeRenderer.line(
				Core.INSTANCE.camera.orthoCam.position.x - Core.INSTANCE.camera.orthoCam.viewportWidth / 2,
				Core.INSTANCE.camera.orthoCam.position.y,
				Core.INSTANCE.camera.orthoCam.position.x + Core.INSTANCE.camera.orthoCam.viewportWidth / 2,
				Core.INSTANCE.camera.orthoCam.position.y
				);
		Core.INSTANCE.shapeRenderer.end();
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
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.input.setInputProcessor(Core.INSTANCE.inputSystem.getProcessor(Inputs.GAME));
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
	}
	
	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}
}
