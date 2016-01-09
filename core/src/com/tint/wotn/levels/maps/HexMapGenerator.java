package com.tint.wotn.levels.maps;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tint.wotn.utils.HexCoordinates;

public class HexMapGenerator {
	public static HexMap generateMap(MapShape mapShape, int radius) {
		return HexMap.createMap(generateTiles(mapShape, radius));
	}
	
	public static Tile[][] generateTiles(MapShape mapShape, int radius) {
		if(radius == 0)	return generateEmpty(radius);
		
		Tile[][] tiles = generateEmpty(radius);
		
		switch(mapShape) {
		case HEXAGON:
			tiles = generateHexagon(tiles, radius);
			break;
		case RECTANGLE:
			break;
		case RHOMBUS:
			break;
		}
		return tiles;
	}

	private static Tile[][] generateEmpty(int radius) {
		Tile[][] tiles = new Tile[2 * radius + 1][2 * radius + 1];
		for(int i = 0; i < tiles.length; i++)
			for(int j = 0; j < tiles[i].length; j++)
				tiles[i][j] = Tile.NULL;
		
		return tiles;
	}
	
	private static Tile[][] generateHexagon(Tile[][] tiles, int radius) {
		if(!isValid(tiles)) return generateEmpty(radius);
		List<Vector3> tilesInRange = HexCoordinates.getAllInRange(
			-radius, radius,
			-radius, radius,
			-radius, radius);
		for(Vector3 cubeCoord : tilesInRange) {
			Vector2 axialCoord = HexCoordinates.transform(cubeCoord);
			tiles[(int) axialCoord.x + radius][(int) axialCoord.y + radius] = Tile.GRASS;
		}
		
		return tiles;
	}
	
	private static boolean isValid(Tile[][] tiles) {
		if(tiles == null) return false;
		for(Tile[] tileArray : tiles)
			for(Tile tile : tileArray)
				if(tile == null) return false;
		return true;
	}
}
