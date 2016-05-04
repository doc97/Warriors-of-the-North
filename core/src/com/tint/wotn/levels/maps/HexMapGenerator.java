package com.tint.wotn.levels.maps;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tint.wotn.utils.HexCoordinates;

/**
 * Generates {@link HexMap}'s using predefined {@link MapShape}'s
 * @author doc97
 *
 */
public class HexMapGenerator {
	public static HexMap generateMap(MapShape mapShape, int radius) {
		return HexMap.createMap(generateTiles(mapShape, radius));
	}
	
	/**
	 * Generates tiles to be used with the Factory pattern and the
	 * {@link HexMap#createMap(Tile[][])}
	 * @param mapShape The shape of the map
	 * @param radius The radius of the map
	 * @return The two-dimensional {@link Tile} array
	 */
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

	/**
	 * Generates a two-dimensional {@link Tile} array with {@link Tile#NULL}'s
	 * @param radius The radius of the map of which the tiles are for
	 * @return The two-dimensional array
	 */
	private static Tile[][] generateEmpty(int radius) {
		Tile[][] tiles = new Tile[2 * radius + 1][2 * radius + 1];
		for(int i = 0; i < tiles.length; i++)
			for(int j = 0; j < tiles[i].length; j++)
				tiles[i][j] = Tile.NULL;
		
		return tiles;
	}
	
	/**
	 * Generates the tiles for a hexagon shaped map
	 * @param tiles The tile array to be edited, can be null to genereate
	 * an empty map
	 * @param radius The radius of the hexagon
	 * @return The edited tile array
	 */
	private static Tile[][] generateHexagon(Tile[][] tiles, int radius) {
		if(!isValid(tiles)) return generateEmpty(radius);
		List<Vector3> tilesInRange = HexCoordinates.getAllInRange(
			-radius, radius,
			-radius, radius,
			-radius, radius);
		for(Vector3 cubeCoord : tilesInRange) {
			Vector2 axialCoord = HexCoordinates.transform(cubeCoord);
			tiles[(int) axialCoord.x + radius][(int) axialCoord.y + radius] = Tile.SNOW;
		}
		
		return tiles;
	}
	
	/**
	 * Checks that the supplied array doesn't contain null elements
	 * @param tiles The array to be checked
	 * @return Whether the array doesn't contain null elements
	 */
	private static boolean isValid(Tile[][] tiles) {
		if(tiles == null) return false;
		for(Tile[] tileArray : tiles)
			for(Tile tile : tileArray)
				if(tile == null) return false;
		return true;
	}
}
