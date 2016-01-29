package com.tint.wotn.missions;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class MissionSystem {
	private List<Mission> availableMissions = new ArrayList<Mission>();
	private List<Mission> completedMissions = new ArrayList<Mission>();
	private List<Mission> unavaiableMissions = new ArrayList<Mission>();
	
	public void initialize() {
		Mission mission0 = new Mission();
		mission0.ID = 0;
		mission0.name = "Mission name";
		mission0.legend = "The beginning was very nice";
		mission0.position = new Vector2(100, 100);
		mission0.unlockIDs = new int[] { 0 };
		availableMissions.add(mission0);
	}

	public void addAvailableMission(Mission mission) {
		if(getAvailableMissionWithID(mission.ID) == null)
			availableMissions.add(mission);
	}
	public void addUnavailableMission(Mission mission) {
		if(getUnavailableMissionWithID(mission.ID) == null)
			unavaiableMissions.add(mission);
	}
	
	public void completeMission(int id) {
		Mission currentMission = getAvailableMissionWithID(id);
		if(currentMission != null) {
			completedMissions.add(currentMission);
			availableMissions.remove(currentMission);
			
			for(int i : currentMission.unlockIDs) {
				if(getAvailableMissionWithID(i) != null) continue;
				Mission unavailableMission = getUnavailableMissionWithID(i);
				if(unavailableMission != null) {
					availableMissions.add(unavailableMission);
				}
			}
		}
	}
	
	private Mission getAvailableMissionWithID(int id) {
		for(Mission m : availableMissions)
			if(m.ID == id)
				return m;
		
		return null;
	}
	
	private Mission getUnavailableMissionWithID(int id) {
		for(Mission m : unavaiableMissions)
			if(m.ID == id)
				return m;
		
		return null;
	}
	
	public List<Mission> getAvailableMissions() {
		return availableMissions;
	}
}
