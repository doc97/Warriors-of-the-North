package com.tint.wotn.story;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.tint.wotn.Core;
import com.tint.wotn.missions.Mission;

public class Quest {

	public static final float WIDTH = 64;
	public static final float HEIGHT = 64;
	
	private final Mission mission;
	private boolean selected;

	private static AtlasRegion completedTexture;
	private static AtlasRegion availableTexture;
	private static AtlasRegion unavailableTexture;
	private static AtlasRegion glowTexture;
	
	public Quest(Mission mission) {
		this.mission = mission;
	}
	
	public static void loadTextures() {
		TextureAtlas atlas = Core.INSTANCE.assets.getTextureAtlas("textures/packed/WarriorsOfTheNorth.atlas");
		completedTexture = atlas.findRegion("quest_marker_completed");
		availableTexture = atlas.findRegion("quest_marker_available");
		unavailableTexture = atlas.findRegion("quest_marker_unavailable");
		glowTexture = atlas.findRegion("quest_marker_glow");
	}

	public void render(SpriteBatch batch) {
		AtlasRegion texture = getTexture();
		if (texture == null) return;
		
		if (selected) {
			batch.draw(glowTexture,
					mission.position.x - WIDTH / 2,
					mission.position.y - HEIGHT / 2,
					WIDTH, HEIGHT * 1.5f);
		}

		batch.draw(texture,
				mission.position.x - WIDTH / 2,
				mission.position.y - HEIGHT / 2,
				WIDTH, HEIGHT);
	}
	
	public void select() {
		selected = true;
	}
	
	public void unselect() {
		selected = false;
	}
	
	public Mission getMission() {
		return mission;
	}
	
	private AtlasRegion getTexture() {
		if (Core.INSTANCE.missionSystem.missionIsUnavailable(mission.ID))
			return unavailableTexture;
		if (Core.INSTANCE.missionSystem.missionIsAvailable(mission.ID))
			return availableTexture;
		if (Core.INSTANCE.missionSystem.missionIsCompleted(mission.ID))
			return completedTexture;
		return null;
	}
}
