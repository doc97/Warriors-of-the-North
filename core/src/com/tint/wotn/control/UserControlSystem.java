package com.tint.wotn.control;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tint.wotn.Core;
import com.tint.wotn.GameMode;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.MovementComponent;
import com.tint.wotn.ecs.components.OwnerComponent;
import com.tint.wotn.ecs.systems.CombatSystem;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.net.constants.Status;
import com.tint.wotn.net.packets.StatusPacket;
import com.tint.wotn.utils.CoordinateConversions;
import com.tint.wotn.utils.HexCoordinates;

public class UserControlSystem {
	
	private Vector2 worldTouchPos = new Vector2();
	private Entity selectedUnit;
	private List<Vector2> selectedTiles = new ArrayList<Vector2>();
	
	public void endTurn() {
		if(!Core.INSTANCE.game.isPlayersTurn()) return;

		if(Core.INSTANCE.gameMode == GameMode.SINGLE_PLAYER) {
			
		} else if(Core.INSTANCE.gameMode == GameMode.MULTI_PLAYER) {
			Core.INSTANCE.game.playerInTurnID = -1;
			StatusPacket endTurnPacket = new StatusPacket();
			endTurnPacket.status = Status.TURN_END;
			Core.INSTANCE.multiplayerSystem.client.sendTCP(endTurnPacket);
		}
	}
	
	public void updateWorldTouchPos(Vector2 updatedVector) {
		worldTouchPos.set(updatedVector);
	}
	
	public void dragCamera(int screenX, int screenY) {
		Vector2 currentTouchPos = CoordinateConversions.screenToWorldPos(screenX, screenY);
		Vector2 delta = worldTouchPos.cpy().sub(currentTouchPos);
		Core.INSTANCE.camera.add(delta.x, delta.y);
		Core.INSTANCE.camera.update();
	}
	
	/**
	 * Handles the touch of a tile
	 * @param target - The target tile coordinate in hex coordinate
	 */
	public void touchTile(Vector2 target) {
		if(!Core.INSTANCE.game.isPlayersTurn()) return;

		Entity targetUnit = Core.INSTANCE.userControlSystem.getUnitAt(target);
		if(targetUnit == null) {
			moveWithSelectedUnit(target);
			unselectUnit();
		} else if(unitIsSelected()) {
			attackWithSelectedUnit(targetUnit);
			unselectUnit();
		} else {
			selectUnit(targetUnit);
		}
	}

	private void unselectUnit() {
		selectedUnit = null;
		for(Vector2 tile : selectedTiles) {
			Core.INSTANCE.gameMode.map.unmarkTile(
					(int) tile.x,
					(int) tile.y,
					true);
		}
		selectedTiles.clear();
	}
	
	private void selectUnit(Entity unit) {
		unselectUnit();
		OwnerComponent owner = Mappers.owner.get(unit);
		if(owner.ownerID != Core.INSTANCE.game.player.id) return;

		selectedUnit = unit;
		MovementComponent movement = Mappers.movement.get(selectedUnit);
			
		List<Vector3> markTiles = HexCoordinates.getAllInRange(
				-movement.range, movement.range,
				-movement.range, movement.range,
				-movement.range, movement.range);
		for(Vector3 cubeCoord : markTiles) {
			Vector2 axialCoord = HexCoordinates.transform(cubeCoord);
			Vector2 actualPos = axialCoord.cpy().add(movement.position);
			if(Core.INSTANCE.gameMode.map.getTile((int) actualPos.x,(int) actualPos.y) == Tile.NULL) continue;

			selectedTiles.add(actualPos);
			Core.INSTANCE.gameMode.map.markTile(
					(int) (axialCoord.x + movement.position.x),
					(int) (axialCoord.y + movement.position.y),
					true);
		}
	}
	
	private void attackWithSelectedUnit(Entity targetUnit) {
		if(!unitIsSelected()) return;
		OwnerComponent selectedUnitOwner = Mappers.owner.get(selectedUnit);
		if(selectedUnitOwner.ownerID != Core.INSTANCE.game.player.id) return;
		OwnerComponent targetUnitOwner = Mappers.owner.get(targetUnit);
		if(selectedUnitOwner.ownerID == targetUnitOwner.ownerID) return;

		Core.INSTANCE.ecs.engine.getSystem(CombatSystem.class).simulate(selectedUnit, targetUnit);
	}
	
	private void moveWithSelectedUnit(Vector2 position) {
		if(!unitIsSelected()) return;
		OwnerComponent owner = Mappers.owner.get(selectedUnit);
		if(owner.ownerID != Core.INSTANCE.game.player.id) return;

		MovementComponent movement = Mappers.movement.get(selectedUnit);
		for(Vector2 tile : selectedTiles) {
			if(tile.x == position.x && tile.y == position.y) {
				movement.position.set(position);
				break;
			}
		}
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

	private boolean unitIsSelected() {
		return selectedUnit != null;
	}
}
