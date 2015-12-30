package com.tint.wotn.levels.maps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.utils.HexCoordinates;

public class Map {
	public Tile[][] tiles;
	public Vector2 position = new Vector2();
	public static boolean initialized;
	
	private Map() {}
	
	public static void initialize() {
		if(initialized) return;
		for(Tile t : Tile.values())
			t.loadTexture();
		initialized = true;
	}
	
	public static Map createMap(Tile[][] tiles) {
		Map map = new Map();
		map.tiles = tiles;
		return map;
	}
	
	public void render(SpriteBatch batch) {
		Vector2 axial = new Vector2();
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[i].length; j++) {
				axial.set(i, j);
				Vector2 tilePos = HexCoordinates.axialToPixel(Tile.SIZE, (int) (Tile.SIZE / 16.0f), axial);
				batch.draw(tiles[i][j].texture,
						position.x + tilePos.x, position.y + tilePos.y,
						Tile.SIZE * 2, Tile.SIZE * 2);
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || x >= tiles.length) return Tile.NULL;
		if(y < 0 || y >= tiles[x].length) return Tile.NULL;
		return tiles[x][y];
	}
}
