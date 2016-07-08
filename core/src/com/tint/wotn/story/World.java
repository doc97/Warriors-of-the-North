package com.tint.wotn.story;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.tint.wotn.Core;
import com.tint.wotn.missions.Mission;

public class World {

	private Texture map;
	private Texture edge;
	private AtlasRegion cloud;
	private List<Quest> quests = new ArrayList<Quest>();
	private Quest selectedQuest;
	
	public void initialize() {
		quests.clear();
		for (Mission mission : Core.INSTANCE.missionSystem.getAllMissions())
			quests.add(new Quest(mission));
	}
	
	public void loadTextures() {
		TextureAtlas atlas = Core.INSTANCE.assets.getTextureAtlas("textures/packed/WarriorsOfTheNorth.atlas");
		map = Core.INSTANCE.assets.getTexture("textures/notpacked/campaign_map.png");
		edge = Core.INSTANCE.assets.getTexture("textures/notpacked/dark_edge_campaign_map.png");
		cloud = atlas.findRegion("campaign_map_clouds");
		Quest.loadTextures();
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(map, 0, 0, Core.INSTANCE.camera.getWidth(), Core.INSTANCE.camera.getHeight());
		for (Quest quest : quests)
			quest.render(batch);
		Core.INSTANCE.camera.center();
		Core.INSTANCE.camera.update();
		batch.setProjectionMatrix(Core.INSTANCE.camera.getOrthoCam().combined);
		batch.draw(edge, 0, 0, Core.INSTANCE.camera.getWidth(), Core.INSTANCE.camera.getHeight());
	}
	
	public void selectQuest(Quest quest) {
		if (selectedQuest != null) selectedQuest.unselect();
		selectedQuest = quest;
		selectedQuest.select();
	}
	
	public void unselectQuest() {
		if (selectedQuest == null) return;
		selectedQuest.unselect();
		selectedQuest = null;
	}
	
	public List<Quest> getQuests() {
		return quests;
	}
}
