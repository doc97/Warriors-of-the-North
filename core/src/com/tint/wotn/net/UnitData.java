package com.tint.wotn.net;

import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.UnitType;

/**
 * Contains data about unit, also used when handling units in combination with
 * networking
 * @author doc97
 *
 */
public class UnitData {
	public int unitID;
	public int ownerID;
	public Vector2 position;
	public UnitType unitType;
}
