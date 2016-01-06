package com.tint.wotn;

import com.tint.wotn.levels.maps.HexMap;
import com.tint.wotn.levels.maps.HexMapGenerator;

public enum GameMode {
	SINGLE_PLAYER, MULTI_PLAYER;
	
	public HexMap map = HexMap.createMap(HexMapGenerator.generate(null, 0));
}
