package com.tint.wotn.save;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.ClientGame;
import com.tint.wotn.Core;
import com.tint.wotn.actions.ActionSystem;
import com.tint.wotn.ecs.EntityData;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.AttackComponent;
import com.tint.wotn.ecs.components.HealthComponent;
import com.tint.wotn.ecs.components.IDComponent;
import com.tint.wotn.ecs.components.MovementComponent;
import com.tint.wotn.ecs.components.OwnerComponent;
import com.tint.wotn.ecs.components.RenderComponent;
import com.tint.wotn.levels.maps.Tile;
import com.tint.wotn.missions.MissionSystem;

public class GameSave implements Serializable {
	private static final long serialVersionUID = -5462433759752919823L;

	private List<EntityData> entities;
	private ClientGame game;
	private MissionSystem missionSystem;
	private ActionSystem actionSystem;
	private boolean inBattle;
	
	private GameSave() {}
	
	public static GameSave createSave() {
		GameSave save = new GameSave();
		save.entities = new ArrayList<EntityData>();
		
		for (Entity e : Core.INSTANCE.ecs.engine.getEntities()) {
			AttackComponent attack = Mappers.attack.get(e);
			HealthComponent health = Mappers.health.get(e);
			MovementComponent movement = Mappers.movement.get(e);
			OwnerComponent owner = Mappers.owner.get(e);
			RenderComponent render = Mappers.render.get(e);
			IDComponent id = Mappers.id.get(e);

			EntityData data = new EntityData();
			if (attack != null) {
				data.attackCost = attack.cost;
				data.damage = attack.damage;
			}
			if (health != null) {
				data.health = health.health;
			}
			if (movement != null) {
				data.movementCost = movement.cost;
				data.position = movement.position;
				data.movementRange = movement.range;
			}
			if (owner != null) {
				data.ownerID = owner.ownerID;
			}
			if (id != null) {
				data.entityID = id.id;
			}
			if (render != null) {
				data.offset = new Vector2(render.offset.x - Tile.SPACING / 2, render.offset.y - Tile.SPACING / 2);
				data.size = render.size;
				data.textureName = render.texture.name;
				data.color = new float[4];
				data.color[0] = render.tintColor.r;
				data.color[1] = render.tintColor.g;
				data.color[2] = render.tintColor.b;
				data.color[3] = render.tintColor.a;
			}
			save.entities.add(data);
		}

		save.missionSystem = Core.INSTANCE.missionSystem;
		save.game = Core.INSTANCE.game;
		save.actionSystem = Core.INSTANCE.actionSystem;
		save.inBattle = Core.INSTANCE.game.isInBattle();
		return save;
	}
	
	public List<EntityData> getEntities() { return entities; }
	public ClientGame getGame() { return game; }
	public MissionSystem getMissionSystem() { return missionSystem; }
	public ActionSystem getActionSystem() { return actionSystem; }
	public boolean isInBattle() { return inBattle; }
}
