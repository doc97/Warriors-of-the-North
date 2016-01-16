package com.tint.wotn.levels.maps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.utils.CoordinateConversions;

public class HexMap {
	public Tile[][] tiles;
	private byte[] markedTileBitMap;
	private byte[] permanentMarkedTileBitMap;
	private static AtlasRegion markedTextureOverlay;
	
	private HexMap() {}
	
	public static void loadTextures() {
		for(Tile t : Tile.values())
			t.loadTexture();
		
		TextureAtlas atlas = Core.INSTANCE.assets.getTextureAtlas("textures/packed/WarriorsOfTheNorth.atlas");
		markedTextureOverlay = atlas.findRegion("marked_tile");
	}
	
	public static HexMap createMap(Tile[][] tiles) {
		HexMap map = new HexMap();
		map.tiles = tiles;
		int tileAmount = 0;
		if(tiles.length > 0)
			tileAmount = tiles.length * tiles[0].length;
		map.initializeMarkedTiles(tileAmount);
		return map;
	}
	
	public void initializeMarkedTiles(int tileAmount) {
		markedTileBitMap = new byte[(int) Math.ceil(tileAmount / 7d)];
		permanentMarkedTileBitMap = new byte[(int) Math.ceil(tileAmount / 7d)];
	}
	
	public void render(SpriteBatch batch) {
		Vector2 axial = new Vector2();
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[i].length; j++) {
				axial.set(i, j);
				Vector2 tilePos = CoordinateConversions.axialToWorld(Tile.SIZE, Tile.SPACING, axial);
				renderTile(batch, tiles[i][j].texture, tilePos);

				if(isMarkedTile(i, j) && getTile(i, j) != Tile.NULL)
					renderTile(batch, markedTextureOverlay, tilePos);
			}
		}
	}
	
	private void renderTile(SpriteBatch batch, AtlasRegion texture, Vector2 position) {
		batch.draw(texture,
				position.x - (Tile.SIZE + Tile.SPACING / 2),
				position.y - (Tile.SIZE + Tile.SPACING / 2),
				Tile.SIZE * 2, Tile.SIZE * 2);
	}
	
	public Tile getTile(int r, int q) {
		if(r < 0 || r >= tiles.length) return Tile.NULL;
		if(q < 0 || q >= tiles[r].length) return Tile.NULL;
		return tiles[r][q];
	}
	
	public boolean isMarkedTile(int r, int q) {
		if(r < 0 || r >= tiles.length || q < 0 || q >= tiles[r].length) return false;
		long index = (r * tiles[r].length + q);
		return ((markedTileBitMap[(int) (Math.floor(index / 7d))] >> (index % 7)) & 1) == 1;
	}
	
	public boolean isPermanentMarkedTile(int r, int q) {
		if(r < 0 || r >= tiles.length || q < 0 || q >= tiles[r].length) return false;
		long index = (r * tiles[r].length + q);
		return ((permanentMarkedTileBitMap[(int) (Math.floor(index / 7d))] >> (index % 7)) & 1) == 1;
	}
	
	public void markTile(int r, int q, boolean permanent) {
		if(r < 0 || r >= tiles.length || q < 0 || q >= tiles[r].length) return;
		long index = (r * tiles[r].length + q);
		markedTileBitMap[(int) (Math.floor(index / 7d))] |= 1 << (index % 7);
		if(permanent)
			permanentMarkedTileBitMap[(int) (Math.floor(index / 7d))] |= 1 << (index % 7);
	}
		
	public void unmarkTile(int r, int q, boolean permanent) {
		if(r < 0 || r >= tiles.length || q < 0 || q >= tiles[r].length) return;
		long index = (r * tiles[r].length + q);
		markedTileBitMap[(int) (Math.floor(index / 7d))] &= ~(1 << (index % 7));
		if(permanent)
			permanentMarkedTileBitMap[(int) (Math.floor(index / 7d))] &= ~(1 << (index % 7));
	}
	
	public void toggleMarkedTile(int r, int q, boolean permanent) {
		if(r < 0 || r >= tiles.length || q < 0 || q >= tiles[r].length) return;
		long index = (r * tiles[r].length + q);
		markedTileBitMap[(int) (Math.floor(index / 7d))] ^= 1 << (index % 7);
		if(permanent)
			permanentMarkedTileBitMap[(int) (Math.floor(index / 7d))] ^= 1 << (index % 7);
	}

	public void resetMarkedTiles() {
		for(int i = 0; i < markedTileBitMap.length; i++) {
			markedTileBitMap[i] = 0;
			permanentMarkedTileBitMap[i] = 0;
		}
	}
	
	public void clearNonPermanentMarkedTiles() {
		for(int i = 0; i < markedTileBitMap.length; i++)
			markedTileBitMap[i] = permanentMarkedTileBitMap[i];
	}
	
	public byte[] getMarkedTiles() {
		return markedTileBitMap;
	}
}
