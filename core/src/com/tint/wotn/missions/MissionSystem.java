package com.tint.wotn.missions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.ui.UIDataStorage;
import com.tint.wotn.ui.UserInterfaces;
import com.tint.wotn.utils.files.ConfigurationFile;
import com.tint.wotn.utils.files.parsers.ConfigurationFileParser;
import com.tint.wotn.utils.files.parsers.ConfigurationParserException;
import com.tint.wotn.utils.files.parsers.KeyValueParser;

/**
 * A system that handles mission-related operations
 * @author doc97
 * @see Mission
 */
public class MissionSystem {

	private List<Mission> availableMissions = new ArrayList<Mission>();
	private List<Mission> completedMissions = new ArrayList<Mission>();
	private List<Mission> unavailableMissions = new ArrayList<Mission>();
	
	private static final int UNAVAILABLE = 0;
	private static final int AVAILABLE = 1;
	private static final int COMPLETED = 2;
	
	/**
	 * Loads missions
	 */
	public void initialize() {
		availableMissions.clear();
		completedMissions.clear();
		unavailableMissions.clear();
		
		File folder = new File("configs/quests/");
		File[] files = folder.listFiles();
		for (File f : files) {
			try {
				ConfigurationFileParser parser = new KeyValueParser();
				ConfigurationFile configFile = new ConfigurationFile(f, parser);
				configFile.parseFile();
				
				Mission mission = createMission(configFile);
				if (mission == null) {
					Gdx.app.log("MissionSystem", "Faulty configuration file!");
					continue;
				}
				if (hasMission(mission.ID)) {
					Gdx.app.log("MissionSystem", "There already exists a mission with ID: " + mission.ID + "!");
					continue;
				}
				
				Gdx.app.log("MissionSystem", "Loading mission: " + mission.name);
				if (mission.status == UNAVAILABLE) addUnavailableMission(mission);
				else if (mission.status == AVAILABLE) addAvailableMission(mission);
				else if (mission.status == COMPLETED) addCompletedMission(mission);
				else Gdx.app.log("MissionSystem", "Mission with unknown status skipped");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ConfigurationParserException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void saveMissions() {
		for (Mission mission : getAllMissions()) {
			Map<String, String> configs = new HashMap<String, String>();
			configs.put("missionID", String.valueOf(mission.ID));
			configs.put("name", mission.name);
			configs.put("legend", mission.legend);
			configs.put("status", String.valueOf(mission.status));
			configs.put("position", mission.position.x + "," + mission.position.y);

			StringBuilder ids = new StringBuilder();
			for (int i = 0; i < mission.unlockIDs.length; i++) {
				ids.append(String.valueOf(mission.unlockIDs[i]));
				if (i < mission.unlockIDs.length - 1)
					ids.append(",");
			}
			configs.put("unlockIDs", ids.toString());
			
			File file = new File("configs/quests/" + mission.filename);
			try {
				ConfigurationFile config = new ConfigurationFile(file,
						new KeyValueParser());
				config.setData(configs);
				config.saveFile();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ConfigurationParserException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Mission createMission(ConfigurationFile config) {
		Mission mission = new Mission();
		try {
			if (!config.hasKey("missionID")) return null;
			if (!config.hasKey("name")) return null;
			if (!config.hasKey("legend")) return null;
			if (!config.hasKey("status")) return null;
			if (!config.hasKey("position")) return null;
			if (!config.hasKey("unlockIDs")) return null;

			mission.filename = config.getFilename();
			mission.ID = Integer.parseInt(config.getValue("missionID"));
			mission.name = config.getValue("name");
			mission.legend = config.getValue("legend");
			mission.status = Integer.parseInt(config.getValue("status"));

			String[] position = config.getValue("position").split("\\s*,\\s*");
			if (position.length < 2) return null;
			float x = Float.parseFloat(position[0]);
			float y = Float.parseFloat(position[1]);
			mission.position = new Vector2(x, y);

			String[] unlockIDStr = config.getValue("unlockIDs").split("\\s*,\\s*");
			mission.unlockIDs = new int[unlockIDStr.length];
			for (int i = 0; i < unlockIDStr.length; i++) {
				if (unlockIDStr[i].isEmpty()) continue;
				mission.unlockIDs[i] = Integer.parseInt(unlockIDStr[i]);
			}
			
			return mission;
		} catch (NumberFormatException nfe)	{
			Gdx.app.log("MissionSystem", "Config value is supposed to be a number", nfe);
		}
		return null;
	}

	private void addAvailableMission(Mission mission) {
		if(getAvailableMissionWithID(mission.ID) == null)
			availableMissions.add(mission);
	}
	
	private void addUnavailableMission(Mission mission) {
		if(getUnavailableMissionWithID(mission.ID) == null)
			unavailableMissions.add(mission);
	}
	
	private void addCompletedMission(Mission mission) {
		if (getCompletedMissionWithID(mission.ID) == null)
			completedMissions.add(mission);
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
