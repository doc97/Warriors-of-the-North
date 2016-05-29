package com.tint.wotn.levels.maps;

import java.io.Serializable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.utils.CoordinateConversions;
import com.tint.wotn.utils.HexCoordinates;

/**
 * Contains data about the hex map tiles. Marked tiles are managed using
 * bitmaps
 * @author doc97
 * @see Tile
 * 
 */
public class HexMap implements Serializable {

	private static final long serialVersionUID = 6640126643600383709L;
	public Tile[][] tiles;
	private byte[] markedTileBitMap;
	private byte[] permanentMarkedTileBitMap;
	private transient static AtlasRegion markedTextureOverlay;
	
	/**
	 * Private constructor for Factory pattern
	 */
	private HexMap() {}
	
	/**
	 * Loads map textures into memory
	 */
	public static void loadTextures() {
		for(Tile t : Tile.values())
			t.loadTexture();
		
		TextureAtlas atlas = Core.INSTANCE.assets.getTextureAtlas("textures/packed/WarriorsOfTheNorth.atlas");
		markedTextureOverlay = atlas.findRegion("marked_tile");
	}
	
	/**
	 * A factory method for creating a {@link HexMap} using a two-dimensional
	 * {@link Tile} array
	 * @param tiles Tile array
	 * @return The created map
	 */
	public static HexMap createMap(Tile[][] tiles) {
		HexMap map = new HexMap();
		map.tiles = tiles;
		int tileAmount = 0;
		if(tiles.length > 0)
			tileAmount = tiles.length * tiles[0].length;
		map.initializeMarkedTiles(tileAmount);
		return map;
	}
	
	private void initializeMarkedTiles(int tileAmount) {
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
	
	/**
	 * Sets a marked flag for the tile at (r, q) in axial coordinates
	 * @param r The "x" component
	 * @param q The "y" component
	 * @param permanent If the flag should be set as permanent as well as
	 * currently marked
	 * @see HexCoordinates
	 */
	public void markTile(int r, int q, boolean permanent) {
		if(r < 0 || r >= tiles.length || q < 0 || q >= tiles[r].length) return;
		long index = (r * tiles[r].length + q);
		markedTileBitMap[(int) (Math.floor(index / 7d))] |= 1 << (index % 7);
		if(permanent)
			permanentMarkedTileBitMap[(int) (Math.floor(index / 7d))] |= 1 << (index % 7);
	}
		
	/**
	 * Removes the marked flag for the tile at (r, q) in axial coordinates
	 * @param r The "x" component
	 * @param q The "y" component
	 * @param permanent If the flag should also be removed from the permanent
	 * bitmap
	 * @see HexCoordinates
	 */
	public void unmarkTile(int r, int q, boolean permanent) {
		if(r < 0 || r >= tiles.length || q < 0 || q >= tiles[r].length) return;
		long index = (r * tiles[r].length + q);
		markedTileBitMap[(int) (Math.floor(index / 7d))] &= ~(1 << (index % 7));
		if(permanent)
			permanentMarkedTileBitMap[(int) (Math.floor(index / 7d))] &= ~(1 << (index % 7));
	}
	
	/**
	 * Toggles the marked flag for the tile at (r, q) in axial coordinates
	 * @param r The "x" component
	 * @param q The "y" component
	 * @param permanent If the permanent status should also be toggled
	 * @see HexCoordinates
	 */
	public void toggleMarkedTile(int r, int q, boolean permanent) {
		if(r < 0 || r >= tiles.length || q < 0 || q >= tiles[r].length) return;
		long index = (r * tiles[r].length + q);
		markedTileBitMap[(int) (Math.floor(index / 7d))] ^= 1 << (index % 7);
		if(permanent)
			permanentMarkedTileBitMap[(int) (Math.floor(index / 7d))] ^= 1 << (index % 7);
	}

	/**
	 * Removes all marked flags
	 */
	public void resetMarkedTiles() {
		for(int i = 0; i < markedTileBitMap.length; i++) {
			markedTileBitMap[i] = 0;
			permanentMarkedTileBitMap[i] = 0;
		}
	}
	
	/**
	 * Removes all flags that are not permanent
	 */
	public void clearNonPermanentMarkedTiles() {
		for(int i = 0; i < markedTileBitMap.length; i++)
			markedTileBitMap[i] = permanentMarkedTileBitMap[i];
	}
	
	public byte[] getMarkedTiles() {
		return markedTileBitMap;
	}
}
