package com.tint.wotn.missions;

import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.levels.Level;

/**
 * Contains data about the mission
 * @author doc97
 * @see MissionSystem
 * @see Level
 */
public class Mission {
	public String filename;
	public String name;
	public String legend;
	public Vector2 position;
	public int ID;
	public int[] unlockIDs;
	public int status;
}
