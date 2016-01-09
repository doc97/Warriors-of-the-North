package com.tint.wotn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tint.wotn.Core;
import com.tint.wotn.net.constants.Status;
import com.tint.wotn.net.packets.StatusPacket;

public class LobbyScreen implements Screen {

	@Override
	public void show() {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Core.INSTANCE.screenSystem.update();
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			StatusPacket statusPacket = new StatusPacket();
			statusPacket.status = Status.CLIENT_READY;
			Core.INSTANCE.multiplayerSystem.client.sendTCP(statusPacket);
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {

	}
}
