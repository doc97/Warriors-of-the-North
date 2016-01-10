package com.tint.wotn.net.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tint.wotn.UnitType;
import com.tint.wotn.levels.maps.HexMap;
import com.tint.wotn.levels.maps.HexMapGenerator;
import com.tint.wotn.levels.maps.MapShape;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.net.LoadoutData;
import com.tint.wotn.net.UnitData;
import com.tint.wotn.utils.HexCoordinates;

public class ServerGame {
	
	// Right now every server game has the hexagon map
	public MapShape mapShape = MapShape.HEXAGON;
	// Right now every server game has a map radius of 8
	public int mapRadius = 8;
	public HexMap map;
	
	public List<UnitData> units = new ArrayList<UnitData>();
	
	public void prepare(List<LoadoutData> loadoutDatas) {
		int playerCount = loadoutDatas.size();
		
		map = HexMapGenerator.generateMap(mapShape, mapRadius);
		if(playerCount <= 3 || playerCount == 6)  {
			createSymmetricalGame(loadoutDatas);
		}
	}
	
	private void createSymmetricalGame(List<LoadoutData> loadoutDatas) {

		// Looping through the load out of every player
		for(int i = 0; i < loadoutDatas.size(); i++) {
			LoadoutData loadoutData = loadoutDatas.get(i);
			
			// Load units into list from a load out list
			List<UnitData> unitDatas = new ArrayList<UnitData>();
			int unitTotalCount = 0;
			for(UnitType unitType : loadoutData.loadout.keySet()) {
				int amount = loadoutData.loadout.get(unitType);
				unitTotalCount += amount;
				for(int j = 0; j < amount; j++) {
					UnitData unitData = new UnitData();
					unitData.ownerID = loadoutData.id;
					unitData.unitType = unitType;
					unitDatas.add(unitData);
				}
			}
			
			Vector2 startTile = getStartTile(i * 6 / loadoutDatas.size());
			List<Vector2> spawnTiles = getSpawnTiles(loadoutDatas, startTile, unitTotalCount);
			for(int j = 0; j < unitTotalCount; j++) {
				unitDatas.get(j).position = spawnTiles.get(j);
			}

			units.addAll(unitDatas);
		}
	}
	
	private Vector2 getStartTile(int direction) {
		Vector2 startTile = new Vector2(mapRadius, mapRadius);
		Vector2 dir = new Vector2();
		dir.mulAdd(HexCoordinates.AXIAL_DIRS[direction], mapRadius);
		return startTile.cpy().add(dir);
	}
	
	private List<Vector2> getSpawnTiles(List<LoadoutData> loadoutDatas, Vector2 centerTile, int unitTotalCount) {
		/*
		 * The algorithm will create the shortest list of tiles on which the
		 * units can be added
		 * It works by checking the ring of tiles at an increasing range
		 * from the center tile, also called start tile.
		 */
		List<Vector2> spawnTiles = new ArrayList<Vector2>();
		spawnTiles.add(centerTile);
		for(int k = 0 ; spawnTiles.size() < unitTotalCount; k++) {
			List<Vector3> ring = HexCoordinates.getAllInRing(HexCoordinates.transform(centerTile), k);
			for(Vector3 v3 : ring) {
				Vector2 v2 = HexCoordinates.transform(v3);
				Tile tile = map.getTile((int) v2.x, (int) v2.y);
				if(tile.accessible) {
					spawnTiles.add(v2);
				}
			}
		}
		return spawnTiles;
	}
	
	public static void main(String[] args) {
		List<LoadoutData> loadoutDatas = new ArrayList<LoadoutData>();
		for(int i = 0; i < 3; i++) {
			LoadoutData ld = new LoadoutData();
			Map<UnitType, Integer> loadout = new HashMap<UnitType, Integer>();
			loadout.put(UnitType.RAIDER, 4);
			ld.id = i;
			ld.loadout = loadout;
			loadoutDatas.add(ld);
		}
		
		ServerGame game = new ServerGame();
		game.prepare(loadoutDatas);
		System.out.println("Clear");
	}
}
