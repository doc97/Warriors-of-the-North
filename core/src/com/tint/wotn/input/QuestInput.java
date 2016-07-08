package com.tint.wotn.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.story.Quest;
import com.tint.wotn.ui.UIDataStorage;
import com.tint.wotn.ui.UserInterfaces;
import com.tint.wotn.utils.CoordinateConversions;

public class QuestInput extends InputAdapter {

	private Quest touchDownQuest;
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touchDownQuest = getTouchedQuest(screenX, screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Quest quest = getTouchedQuest(screenX, screenY);
		if (quest == null) return false;
		if (quest != touchDownQuest) {
			touchDownQuest = null;
			return false;
		}
		
		UIDataStorage storage = Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.CAMPAIGN_SCREEN_UI).getStorage();
		storage.storeData("Data", "Briefing name", quest.getMission().name);
		storage.storeData("Data", "Briefing legend", quest.getMission().legend);
		storage.storeData("Data", "Briefing visible", "true");
		storage.storeData("Data", "Page visible", "false");
		Core.INSTANCE.levelSystem.setCurrentLevel(quest.getMission().ID);
		Core.INSTANCE.world.selectQuest(quest);

		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Quest quest = getTouchedQuest(screenX, screenY);
		if (quest != null) {
			Core.INSTANCE.world.selectQuest(quest);
			return true;
		} else {
			Core.INSTANCE.world.unselectQuest();
			return false;
		}
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		Quest quest = getTouchedQuest(screenX, screenY);
		if (quest != null) {
			Core.INSTANCE.world.selectQuest(quest);
			return true;
		} else {
			Core.INSTANCE.world.unselectQuest();
			return false;
		}
	}

	public Quest getTouchedQuest(int screenX, int screenY) {
		Vector2 worldPos = CoordinateConversions.screenToWorldPos(screenX, screenY);
		for (Quest quest : Core.INSTANCE.world.getQuests()) {
			Vector2 questPos = quest.getMission().position;
			boolean inBounds = worldPos.x >= questPos.x - Quest.WIDTH / 2 &&
					worldPos.x <= questPos.x + Quest.WIDTH / 2 &&
					worldPos.y >= questPos.y - Quest.HEIGHT / 2 &&
					worldPos.y <= questPos.y + Quest.HEIGHT / 2;
					
			if (inBounds && Core.INSTANCE.missionSystem.missionIsAvailable(quest.getMission().ID))
				return quest;
		}
		return null;
	}
}
