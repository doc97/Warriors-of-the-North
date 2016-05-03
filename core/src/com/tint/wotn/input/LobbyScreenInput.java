package com.tint.wotn.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.tint.wotn.Core;
import com.tint.wotn.net.constants.Status;
import com.tint.wotn.net.packets.StatusPacket;

public class LobbyScreenInput extends InputAdapter {

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.SPACE) {
			Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
			StatusPacket statusPacket = new StatusPacket();
			statusPacket.status = Status.CLIENT_READY;
			Core.INSTANCE.multiplayerSystem.client.sendTCP(statusPacket);
		} else if (keycode == Keys.ESCAPE) {
			Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
			StatusPacket statusPacket = new StatusPacket();
			statusPacket.status = Status.CLIENT_NOT_READY;
			Core.INSTANCE.multiplayerSystem.client.sendTCP(statusPacket);
		}

		return false;
	}
}
