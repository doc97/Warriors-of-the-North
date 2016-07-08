package com.tint.wotn.missions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.ui.UIDataStorage;
import com.tint.wotn.ui.UserInterfaces;

/**
 * A system that handles mission-related operations
 * @author doc97
 * @see Mission
 */
public class MissionSystem implements Serializable {

	private static final long serialVersionUID = -8841515036166414609L;
	private List<Mission> availableMissions = new ArrayList<Mission>();
	private List<Mission> completedMissions = new ArrayList<Mission>();
	private List<Mission> unavailableMissions = new ArrayList<Mission>();
	
	/**
	 * Loads missions
	 */
	public void initialize() {
		Mission mission0 = new Mission();
		mission0.ID = 0;
		mission0.name = "Mission #1";
		mission0.legend = "The beginning was very nice";
		mission0.position = new Vector2(100, 100);
		mission0.unlockIDs = new int[] { 1 };
		addAvailableMission(mission0);
		
		Mission mission1 = new Mission();
		mission1.ID = 1;
		mission1.name = "Mission #2";
		mission1.legend = "The beginning continued nicely";
		mission1.position = new Vector2(400, 100);
		mission1.unlockIDs = new int[] { };
		addUnavailableMission(mission1);
	}

	private void addAvailableMission(Mission mission) {
		if(getAvailableMissionWithID(mission.ID) == null)
			availableMissions.add(mission);
	}
	
	private void addUnavailableMission(Mission mission) {
		if(getUnavailableMissionWithID(mission.ID) == null)
			unavailableMissions.add(mission);
	}
	
	/**
	 * Moves the current mission from the available list to the completed list.
	 * <br>
	 * Unlocks new missions by moving unlocked missions from the
	 * unavailable list to the available
	 * @param id ID for the mission to complete
	 * @param reward If true then reward is given
	 */
	public void completeMission(int id, boolean reward) {
		Mission completedMission = getAvailableMissionWithID(id);
		if(completedMission != null) {
			completedMissions.add(completedMission);
			availableMissions.remove(completedMission);
			
			Core.INSTANCE.story.nextPage();

			UIDataStorage storage = Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.CAMPAIGN_SCREEN_UI).getStorage();
			storage.storeData("Data", "Page visible", "true");
			storage.storeData("Data", "Page text", Core.INSTANCE.story.getCurrentPage().getText());
			
			for (int i : completedMission.unlockIDs) {
				if (!missionIsUnavailable(i)) continue;
				Mission unavailableMission = getUnavailableMissionWithID(i);
				availableMissions.add(unavailableMission);
				unavailableMissions.remove(unavailableMission);
			}
		}
	}
	
	public boolean missionIsAvailable(int id) {
		return getAvailableMissionWithID(id) != null;
	}
	
	public boolean missionIsCompleted(int id) {
		return getCompletedMissionWithID(id) != null;
	}

	public boolean missionIsUnavailable(int id) {
		return getUnavailableMissionWithID(id) != null;
	}
	
	public boolean hasMission(int id) {
		for (Mission m : getAllMissions())
			if (m.ID == id)
				return true;
		
		return false;
	}

	public Mission getAvailableMissionWithID(int id) {
		for(Mission m : availableMissions)
			if(m.ID == id)
				return m;
		
		return null;
	}
	
	public Mission getCompletedMissionWithID(int id) {
		for(Mission m : completedMissions)
			if(m.ID == id)
				return m;
		
		return null;
	}
	
	public Mission getUnavailableMissionWithID(int id) {
		for(Mission m : unavailableMissions)
			if(m.ID == id)
				return m;
		
		return null;
	}
	
	public Mission getMissionWithID(int id) {
		for (Mission m : getAllMissions())
			if (m.ID == id)
				return m;
		
		return null;
	}
	
	public List<Mission> getAvailableMissions() {
		return availableMissions;
	}
	
	public List<Mission> getUnavailableMissions() {
		return unavailableMissions;
	}
	
	public List<Mission> getCompletedMissions() {
		return completedMissions;
	}
	
	public List<Mission> getAllMissions() {
		List<Mission> all = new ArrayList<Mission>();
		all.addAll(availableMissions);
		all.addAll(unavailableMissions);
		all.addAll(completedMissions);
		return all;
	}
}
