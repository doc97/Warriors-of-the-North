package com.tint.wotn.levels.maps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.utils.CoordinateConversions;

public class HexMap {
	public Tile[][] tiles;
	
	private HexMap() {}
	
	public static void loadTextures() {
		for(Tile t : Tile.values())
			t.loadTexture();
	}
	
	public static HexMap createMap(Tile[][] tiles) {
		HexMap map = new HexMap();
		map.tiles = tiles;
		return map;
	}
	
	public void render(SpriteBatch batch) {
		Vector2 axial = new Vector2();
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[i].length; j++) {
				axial.set(i, j);
				Vector2 tilePos = CoordinateConversions.axialToWorld(Tile.SIZE, Tile.SPACING, axial);
				batch.draw(tiles[i][j].texture,
						tilePos.x - (Tile.SIZE + Tile.SPACING / 2),
						tilePos.y - (Tile.SIZE + Tile.SPACING / 2),
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
