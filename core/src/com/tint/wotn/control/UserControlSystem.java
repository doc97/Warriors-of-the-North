package com.tint.wotn.control;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tint.wotn.Core;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.MovementComponent;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.utils.CoordinateConversions;
import com.tint.wotn.utils.HexCoordinates;

public class UserControlSystem {
	
	private Vector2 worldTouchPos = new Vector2();
	private Entity selectedUnit;
	private List<Vector2> selectedTiles = new ArrayList<Vector2>();
	
	public void updateWorldTouchPos(Vector2 updatedVector) {
		worldTouchPos.set(updatedVector);
	}
	
	public void dragCamera(int screenX, int screenY) {
		Vector2 currentTouchPos = CoordinateConversions.screenToWorld(screenX, screenY);
		Vector2 delta = worldTouchPos.cpy().sub(currentTouchPos);
		Core.INSTANCE.camera.add(delta.x, delta.y);
		Core.INSTANCE.camera.update();
	}

	public boolean selectUnit(Vector2 hexCoord) {
		Entity unit = getUnitAt(hexCoord);
		if(unit == null) return false;

		selectedUnit = unit;
		MovementComponent movement = Mappers.movement.get(selectedUnit);
		List<Vector3> markTiles = HexCoordinates.getAllInRange(
				-movement.range, movement.range,
				-movement.range, movement.range,
				-movement.range, movement.range);
		for(Vector3 cubeCoord : markTiles) {
			Vector2 axialCoord = HexCoordinates.transform(cubeCoord);
			Vector2 actualPos = axialCoord.cpy().add(movement.position);
			if(Core.INSTANCE.levelSystem.getCurrentLevel().map.getTile((int) actualPos.x,(int) actualPos.y) == Tile.NULL) continue;

			selectedTiles.add(actualPos);
			Core.INSTANCE.levelSystem.getCurrentLevel().map.toggleMarkedTile(
					(int) (axialCoord.x + movement.position.x),
					(int) (axialCoord.y + movement.position.y),
					true);
		}
		return true;
	}
	
	private Entity getUnitAt(Vector2 hexCoord) {
		@SuppressWarnings("unchecked")
		ImmutableArray<Entity> entities = Core.INSTANCE.ecs.engine.getEntitiesFor(Family.all(MovementComponent.class).get());
		for(Entity entity : entities) {
			MovementComponent movement = Mappers.movement.get(entity);
			if(movement.position.x == hexCoord.x && movement.position.y == hexCoord.y) {
				return entity;
			}
		}
		return null;
	}
	
	public boolean moveSelectedUnit(Vector2 position) {
		if(selectedUnit == null) return false;
		MovementComponent movement = Mappers.movement.get(selectedUnit);
		for(Vector2 tile : selectedTiles) {
			if(tile.x == position.x && tile.y == position.y) {
				movement.position.set(position);
				selectedUnit = null;
				break;
			}
		}
		
		if(selectedUnit == null) {
			for(Vector2 tile : selectedTiles)
				Core.INSTANCE.levelSystem.getCurrentLevel().map.unmarkTile((int) tile.x, (int) tile.y, true);
			selectedTiles.clear();
			return true;
		}
		return false;
	}
}
