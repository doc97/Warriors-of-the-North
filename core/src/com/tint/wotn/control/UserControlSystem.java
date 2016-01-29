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
import com.tint.wotn.actions.AttackAction;
import com.tint.wotn.actions.MoveAction;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.AttackComponent;
import com.tint.wotn.ecs.components.IDComponent;
import com.tint.wotn.ecs.components.MovementComponent;
import com.tint.wotn.ecs.components.OwnerComponent;
import com.tint.wotn.net.constants.Status;
import com.tint.wotn.net.packets.ActionPacket;
import com.tint.wotn.net.packets.StatusPacket;
import com.tint.wotn.utils.HexCoordinates;

public class UserControlSystem {
	
	private Entity selectedUnit;
	private List<Vector2> selectedTiles = new ArrayList<Vector2>();
	
	public void endTurn() {
		if(!Core.INSTANCE.game.isPlayersTurn()) return;

		if(Core.INSTANCE.gameMode == GameMode.SINGLEPLAYER) {
			Core.INSTANCE.game.startTurn();
		} else if(Core.INSTANCE.gameMode == GameMode.MULTIPLAYER) {
			Core.INSTANCE.game.playerInTurnID = -1;
			StatusPacket endTurnPacket = new StatusPacket();
			endTurnPacket.status = Status.TURN_END;
			Core.INSTANCE.multiplayerSystem.client.sendTCP(endTurnPacket);
		}
	}
	
	public void dragCamera(float deltaX, float deltaY) {
		Core.INSTANCE.camera.add(deltaX * Core.INSTANCE.camera.orthoCam.zoom, deltaY * Core.INSTANCE.camera.orthoCam.zoom);
		Core.INSTANCE.camera.update();
	}
	
	/**
	 * Handles the touch of a tile
	 * @param target - The target tile coordinate in hex coordinate
	 */
	public void touchTile(Vector2 target) {
		if(!Core.INSTANCE.game.isPlayersTurn()) return;
		if(Core.INSTANCE.actionSystem.getActionPoints() <= 0) return;

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
			Core.INSTANCE.game.map.unmarkTile(
					(int) tile.x,
					(int) tile.y,
					true);
		}
		selectedTiles.clear();
	}
	
	private void selectUnit(Entity unit) {
		unselectUnit();
		OwnerComponent owner = Mappers.owner.get(unit);
		if(owner == null || owner.ownerID != Core.INSTANCE.game.player.id) return;

		selectedUnit = unit;
		MovementComponent movement = Mappers.movement.get(selectedUnit);
			
		List<Vector3> markTiles = HexCoordinates.getAllInRange(
				-movement.range, movement.range,
				-movement.range, movement.range,
				-movement.range, movement.range);
		for(Vector3 cubeCoord : markTiles) {
			Vector2 axialCoord = HexCoordinates.transform(cubeCoord);
			Vector2 actualPos = axialCoord.cpy().add(movement.position);
			if(!Core.INSTANCE.game.map.getTile((int) actualPos.x,(int) actualPos.y).accessible) continue;
			Entity entityAtActual = getUnitAt(actualPos);
			if(entityAtActual != null) {
				OwnerComponent unitAtTileOwner = Mappers.owner.get(entityAtActual);
				if(unitAtTileOwner != null && unitAtTileOwner.ownerID == owner.ownerID) continue;
			}
			
			selectedTiles.add(actualPos);
			Core.INSTANCE.game.map.markTile(
					(int) (axialCoord.x + movement.position.x),
					(int) (axialCoord.y + movement.position.y),
					true);
		}
	}
	
	private void attackWithSelectedUnit(Entity targetUnit) {
		if(!unitIsSelected()) return;
		OwnerComponent selectedUnitOwner = Mappers.owner.get(selectedUnit);
		IDComponent selectedUnitID = Mappers.id.get(selectedUnit);
		if(selectedUnitOwner == null || selectedUnitOwner.ownerID != Core.INSTANCE.game.player.id) return;
		if(selectedUnitID == null) return;
		OwnerComponent targetUnitOwner = Mappers.owner.get(targetUnit);
		IDComponent targetUnitID = Mappers.id.get(targetUnit);
		if(targetUnitOwner != null && selectedUnitOwner.ownerID == targetUnitOwner.ownerID) return;
		if(targetUnitID == null) return;
		
		AttackComponent attack = Mappers.attack.get(selectedUnit);
		AttackAction action = new AttackAction();
		action.attackerID = selectedUnitID.id;
		action.defenderID = targetUnitID.id;
		action.cost = attack.cost;
		Core.INSTANCE.actionSystem.addAction(action);
		
		if(Core.INSTANCE.gameMode == GameMode.MULTIPLAYER) {
			ActionPacket actionPacket = new ActionPacket();
			actionPacket.action = action;
			Core.INSTANCE.multiplayerSystem.client.sendTCP(actionPacket);
		}
	}
	
	private void moveWithSelectedUnit(Vector2 position) {
		if(!unitIsSelected()) return;
		OwnerComponent selectedUnitOwner = Mappers.owner.get(selectedUnit);
		IDComponent selectedUnitID = Mappers.id.get(selectedUnit);
		if(selectedUnitOwner == null || selectedUnitOwner.ownerID != Core.INSTANCE.game.player.id) return;
		if(selectedUnitID == null) return;

		for(Vector2 tile : selectedTiles) {
			if(tile.x == position.x && tile.y == position.y) {
				MovementComponent movement = Mappers.movement.get(selectedUnit);
				MoveAction action = new MoveAction();
				action.entityID = selectedUnitID.id;
				action.position = position;
				action.cost = movement.cost;
				Core.INSTANCE.actionSystem.addAction(action);
				
				if(Core.INSTANCE.gameMode == GameMode.MULTIPLAYER) {
					ActionPacket actionPacket = new ActionPacket();
					actionPacket.action = action;
					Core.INSTANCE.multiplayerSystem.client.sendTCP(actionPacket);
				}
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
