package com.tint.wotn.save;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.tint.wotn.Core;
import com.tint.wotn.ecs.EntityData;
import com.tint.wotn.ecs.Mappers;
import com.tint.wotn.ecs.components.AttackComponent;
import com.tint.wotn.ecs.components.HealthComponent;
import com.tint.wotn.ecs.components.IDComponent;
import com.tint.wotn.ecs.components.MovementComponent;
import com.tint.wotn.ecs.components.OwnerComponent;
import com.tint.wotn.ecs.components.RenderComponent;
import com.tint.wotn.levels.maps.Tile;

public class GameSave implements Serializable {
	private static final long serialVersionUID = -5462433759752919823L;

	private List<EntityData> entities;
	
	private String playerName;
	private int playerID;

	private Tile[][] tiles;
	private boolean inBattle;
	private int playerInTurnID;
	private int levelID;

	private int currentChapter;
	private int currentParagraph;
	private int currentPage;
	
	private int actionPoints;
	
	public void createSave() {
		saveBattle();
		saveGameSystems();
	}
	
	private void saveBattle() {
		if (!Core.INSTANCE.game.isInBattle()) return;

		entities = new ArrayList<EntityData>();
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
			entities.add(data);
		}

		inBattle = Core.INSTANCE.game.isInBattle();
		tiles = Core.INSTANCE.game.getMap().tiles;
		playerName = Core.INSTANCE.game.getPlayer().getName();
		playerID = Core.INSTANCE.game.getPlayer().getID();
		playerInTurnID = Core.INSTANCE.game.getPlayerInTurnID();
		actionPoints = Core.INSTANCE.actionSystem.getActionPoints();
		levelID = Core.INSTANCE.levelSystem.getCurrentLevelID();
	}
	
	private void saveGameSystems() {
		Core.INSTANCE.missionSystem.saveMissions();
		currentChapter = Core.INSTANCE.story.getCurrentChapterIndex();
		currentParagraph = Core.INSTANCE.story.getCurrentParagraphIndex();
		currentPage = Core.INSTANCE.story.getCurrentPageIndex();
	}
	
	public List<EntityData> getEntities() { return entities; }
	public Tile[][] getTiles() { return tiles; }
	public String getPlayerName() { return playerName; }
	public int getPlayerID() { return playerID; }
	public int getPlayerInTurnID() { return playerInTurnID; }
	public int getActionPoints() { return actionPoints; }
	public int getLevelID() { return levelID; }
	public int getCurrentChapter() { return currentChapter; }
	public int getCurrentParagraph() { return currentParagraph; }
	public int getCurrentPage() { return currentPage; }
	public boolean isInBattle() { return inBattle; }
}