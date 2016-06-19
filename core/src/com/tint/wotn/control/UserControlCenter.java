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
import com.tint.wotn.utils.fsm.FiniteStateMachine;
import com.tint.wotn.utils.fsm.State;
import com.tint.wotn.utils.fsm.StateTransition;

/**
 * A system that handles all user input. This is a kind of control center.
 * @author doc97
 *
 */
public class UserControlCenter {
	
	private Entity selectedUnit;
	private Entity targetUnit;
	private Vector2 touchTile;
	private List<Vector2> selectedTiles;
	private FiniteStateMachine controlStates;
	private boolean actionPerformed;
	private boolean hasChanged;
	
	public void initialize() {
		selectedTiles = new ArrayList<Vector2>();
		controlStates = new FiniteStateMachine();

		State selectedState = new State(controlStates) {
			@Override
			public void enter() {
				selectUnit(targetUnit);

				if (Core.INSTANCE.game.isPlayersTurn() &&
				Core.INSTANCE.actionSystem.getActionPoints() > 0 &&
				unitIsOwners(targetUnit)) {
					updateMap(true);
				} else {
					updateMap(false);
				}
			}
		};
		State notSelectedState = new State(controlStates) {
			@Override
			public void enter() {
				unselectUnit();
			}
		};
		State attackState = new State(controlStates) {
			@Override
			public void enter() {
				attackEntity(selectedUnit, targetUnit);
				actionPerformed = true;
			}

			@Override
			public void exit() {
				actionPerformed = false;
			}
		};
		State moveState = new State(controlStates) {
			@Override
			public void enter() {
				moveEntity(selectedUnit, touchTile);
				actionPerformed = true;
			}

			@Override
			public void exit() {
				actionPerformed = false;
			}
		};
		
		selectedState.addTransition(new StateTransition(selectedState) {
			@Override
			public boolean isReady() {
				if (!hasChanged) return false;
				if (selectedTiles.contains(touchTile)) return false;
				if (targetUnit == null) return false;
				return true;
			}
		});
		
		selectedState.addTransition(new StateTransition(notSelectedState) {
			@Override
			public boolean isReady() {
				if (!hasChanged) return false;
				if (selectedTiles.contains(touchTile)) return false;
				if (targetUnit != null) return false;
				return true;
			}
		});

		selectedState.addTransition(new StateTransition(attackState) {
			@Override
			public boolean isReady() {
				if (!hasChanged) return false;
				if (!Core.INSTANCE.game.isPlayersTurn()) return false;
				if (!selectedTiles.contains(touchTile)) return false;
				if (!unitIsOwners(selectedUnit)) return false;
				if (unitIsOwners(targetUnit)) return false;
				return true;
			}
		});

		selectedState.addTransition(new StateTransition(moveState) {
			@Override
			public boolean isReady() {
				if (!hasChanged) return false;
				if (!Core.INSTANCE.game.isPlayersTurn()) return false;
				if (!selectedTiles.contains(touchTile)) return false;
				if (!unitIsOwners(selectedUnit)) return false;
				if (targetUnit != null) return false;
				return true;
			}
		});
		
		notSelectedState.addTransition(new StateTransition(selectedState) {
			@Override
			public boolean isReady() {
				if (!hasChanged) return false;
				if (targetUnit == null) return false;
				return true;
			}
		});
		
		attackState.addTransition(new StateTransition(notSelectedState) {
			@Override
			public boolean isReady() {
				return actionPerformed;
			}
		});
		
		moveState.addTransition(new StateTransition(notSelectedState) {
			@Override
			public boolean isReady() {
				return actionPerformed;
			}
		});
		
		;
		
		controlStates.addState(selectedState);
		controlStates.addState(notSelectedState);
		controlStates.addState(attackState);
		controlStates.addState(moveState);
		controlStates.setCurrentState(notSelectedState);
	}
	
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
		hasChanged = true;
		touchTile = target;
		targetUnit = Core.INSTANCE.ucc.getUnitAt(target);
		controlStates.update();
		hasChanged = false;
		
		controlStates.update();
	}

	private void unselectUnit() {
		selectedUnit = null;
		updateMap(false);
		
		Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.BATTLE_SCREEN_UI)
			.getStorage().storeData("Data", "Health", "");
		Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.BATTLE_SCREEN_UI)
			.getStorage().storeData("Data", "Attack", "");
	}
	
	private void selectUnit(Entity unit) {
		selectedUnit = unit;
		AttackComponent attack = Mappers.attack.get(unit);
		HealthComponent health = Mappers.health.get(unit);
		Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.BATTLE_SCREEN_UI)
			.getStorage().storeData("Data", "Health", "Health: " + health.health);
		Core.INSTANCE.UISystem.getUserInterface(UserInterfaces.BATTLE_SCREEN_UI)
			.getStorage().storeData("Data", "Attack", "Attack: " + attack.damage);
	}
	
	private void updateMap(boolean show) {
		if (show) 	showActions();
		else		hideActions();
	}
	
	private void showActions() {
		MovementComponent movement = Mappers.movement.get(selectedUnit);
		List<Vector3> markedTilesOffsets = HexCoordinates.getAllInRange(
				-movement.range, movement.range,
				-movement.range, movement.range,
				-movement.range, movement.range);
		for(Vector3 cubeOffsetCoord : markedTilesOffsets) {
			if (cubeOffsetCoord.isZero()) continue;

			Vector2 axialOffsetCoord = HexCoordinates.transform(cubeOffsetCoord);
			Vector2 translatedAxialCoord = axialOffsetCoord.cpy().add(movement.position);
			if(!Core.INSTANCE.game.getMap().getTile(
					(int) translatedAxialCoord.x,
					(int) translatedAxialCoord.y
				).accessible) continue;

			Entity entityAtTile = getUnitAt(translatedAxialCoord);
			if (unitIsOwners(entityAtTile)) continue;
			
			selectedTiles.add(translatedAxialCoord);
			Core.INSTANCE.game.getMap().markTile(
					(int) (translatedAxialCoord.x),
					(int) (translatedAxialCoord.y),
					true);
		}
	}
	
	private void hideActions() {
		Core.INSTANCE.game.getMap().clearNonPermanentMarkedTiles();
		for(Vector2 tile : selectedTiles) {
			Core.INSTANCE.game.getMap().unmarkTile(
					(int) tile.x,
					(int) tile.y,
					true);
		}
		selectedTiles.clear();
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
		if (attacker == null || defender == null) return;
		IDComponent attackerID = Mappers.id.get(attacker);
		IDComponent defenderID = Mappers.id.get(defender);
		AttackComponent attack = Mappers.attack.get(selectedUnit);
		if (attackerID == null || defenderID == null) return;
		if (attack == null) return;
		
		AttackAction action = new AttackAction();
		action.attackerID = attackerID.id;
		action.defenderID = defenderID.id;
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
		if (entity == null) return false;
		OwnerComponent owner = Mappers.owner.get(entity);
		return owner != null && owner.ownerID == Core.INSTANCE.game.getPlayer().getID();
	}
}
