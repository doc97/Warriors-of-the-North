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
import com.tint.wotn.ecs.components.HealthComponent;
import com.tint.wotn.ecs.components.IDComponent;
import com.tint.wotn.ecs.components.MovementComponent;
import com.tint.wotn.ecs.components.OwnerComponent;
import com.tint.wotn.net.constants.Status;
import com.tint.wotn.net.packets.StatusPacket;
import com.tint.wotn.ui.UserInterfaces;
import com.tint.wotn.utils.HexCoordinates;

/**
 * A system that handles all user input. This is a kind of control center.
 * @author doc97
 *
 */
public class UserControlSystem {
	
	private Entity selectedUnit;
	private List<Vector2> selectedTiles = new ArrayList<Vector2>();
	
	/**
	 * Ends the player turn if it is their turn. Acting differently depending
	 * on game mode
	 */
	public void endTurn() {
		if(!Core.INSTANCE.game.isPlayersTurn()) return;

		if(Core.INSTANCE.gameMode == GameMode.SINGLEPLAYER) {
			Core.INSTANCE.game.startTurn();
		} else if(Core.INSTANCE.gameMode == GameMode.MULTIPLAYER) {
			Core.INSTANCE.game.setPlayerInTurn(-1);
			StatusPacket endTurnPacket = new StatusPacket();
			endTurnPacket.status = Status.TURN_END;
			Core.INSTANCE.multiplayerSystem.client.sendTCP(endTurnPacket);
		}
	}
	
	public void dragCamera(float deltaX, float deltaY) {
		Core.INSTANCE.camera.add(deltaX, deltaY);
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
			if(unitIsOwners(targetUnit)) {
				unselectUnit();
				selectUnit(targetUnit);
			} else {
				attackWithSelectedUnit(targetUnit);
				unselectUnit();
			}
		} else {
			selectUnit(targetUnit);
		}
	}

	private void unselectUnit() {
		selectedUnit = null;
		for(Vector2 tile : selectedTiles) {
			Core.INSTANCE.game.getMap().unmarkTile(
					(int) tile.x,
					(int) tile.y,
					true);
		}
		selectedTiles.clear();
		
		Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.BATTLE_SCREEN_UI)
			.getStorage().storeData("Data", "Health", "");
		Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.BATTLE_SCREEN_UI)
			.getStorage().storeData("Data", "Attack", "");
	}
	
	private void selectUnit(Entity unit) {
		AttackComponent attack = Mappers.attack.get(unit);
		HealthComponent health = Mappers.health.get(unit);
		Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.BATTLE_SCREEN_UI)
			.getStorage().storeData("Data", "Health", "Health: " + health.health);
		Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.BATTLE_SCREEN_UI)
			.getStorage().storeData("Data", "Attack", "Attack: " + attack.damage);
		
		OwnerComponent owner = Mappers.owner.get(unit);
		if(owner == null) return;
		selectedUnit = unit;
		
		if(owner.ownerID == Core.INSTANCE.game.getPlayer().getID()) {
			
		}
	}
	
	private void updateMap() {
		MovementComponent movement = Mappers.movement.get(selectedUnit);
		List<Vector3> markTiles = HexCoordinates.getAllInRange(
				-movement.range, movement.range,
				-movement.range, movement.range,
				-movement.range, movement.range);
		for(Vector3 cubeCoord : markTiles) {
			Vector2 axialCoord = HexCoordinates.transform(cubeCoord);
			Vector2 actualPos = axialCoord.cpy().add(movement.position);
			if(!Core.INSTANCE.game.getMap().getTile((int) actualPos.x,(int) actualPos.y).accessible) continue;
			Entity entityAtActual = getUnitAt(actualPos);
			if(entityAtActual != null) {
				OwnerComponent unitAtTileOwner = Mappers.owner.get(entityAtActual);
				if(unitAtTileOwner != null && unitAtTileOwner.ownerID == owner.ownerID) continue;
			}
			
			selectedTiles.add(actualPos);
			Core.INSTANCE.game.getMap().markTile(
					(int) (axialCoord.x + movement.position.x),
					(int) (axialCoord.y + movement.position.y),
					true);
		}
	}
	
	private void attackWithSelectedUnit(Entity targetUnit) {
		if(!unitIsSelected()) return;
		attackEntity(selectedUnit, targetUnit);
	}
	
	private void moveWithSelectedUnit(Vector2 position) {
		if(!unitIsSelected()) return;
		
		for(Vector2 tile : selectedTiles) {
			if(tile.x == position.x && tile.y == position.y) {
				moveEntity(selectedUnit, position);
				break;
			}
		}
	}
	
	private void moveEntity(Entity entity, Vector2 position) {
		IDComponent idComponent = Mappers.id.get(entity);
		OwnerComponent ownerComponent = Mappers.owner.get(entity);
		MovementComponent movementComponent = Mappers.movement.get(entity);
		if (ownerComponent == null) return;
		if (ownerComponent.ownerID != Core.INSTANCE.game.getPlayer().getID()) return;
		if (idComponent == null) return;
		
		MoveAction action = new MoveAction();
		action.entityID = idComponent.id;
		action.position = position;
		action.cost = movementComponent.cost;
		Core.INSTANCE.actionSystem.addClientAction(action);
	}
	
	private void attackEntity(Entity attacker, Entity defender) {
		IDComponent attackerID = Mappers.id.get(attacker);
		IDComponent defenderID = Mappers.id.get(defender);
		OwnerComponent attackerOwner = Mappers.owner.get(attacker);
		OwnerComponent defenderOwner = Mappers.owner.get(defender);
		AttackComponent attack = Mappers.attack.get(selectedUnit);
		if (attackerOwner == null) return;
		if (attackerOwner.ownerID != Core.INSTANCE.game.getPlayer().getID()) return;
		if (defenderOwner == null || attackerOwner.ownerID == defenderOwner.ownerID) return;
		if (attackerID == null || defenderID == null) return;
		if (attack == null) return;
		
		AttackAction action = new AttackAction();
		action.attackerID = attackerOwner.ownerID;
		action.defenderID = defenderOwner.ownerID;
		action.cost = attack.cost;
		Core.INSTANCE.actionSystem.addClientAction(action);
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

	private boolean unitIsOwners(Entity entity) {
		OwnerComponent owner = Mappers.owner.get(entity);
		return !(owner == null || owner.ownerID != Core.INSTANCE.game.getPlayer().getID());
	}
	
	private boolean unitIsSelected() {
		return selectedUnit != null;
	}
}
