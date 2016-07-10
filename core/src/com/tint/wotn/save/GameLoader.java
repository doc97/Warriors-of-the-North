package com.tint.wotn.save;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.tint.wotn.Core;
import com.tint.wotn.ecs.EntityData;
import com.tint.wotn.levels.maps.HexMap;
import com.tint.wotn.levels.maps.HexMapGenerator;
import com.tint.wotn.screens.Screens;
import com.tint.wotn.utils.UnitFactory;

public class GameLoader {

	public static boolean save(String filename) {
		Gdx.app.log("GameLoader", "Saving game...");
		try {
			File file = new File(filename);
			file.getParentFile().mkdirs();
			OutputStream fileStream = new FileOutputStream(file);
			OutputStream bufferStream = new BufferedOutputStream(fileStream);
			ObjectOutputStream out = new ObjectOutputStream(bufferStream);
			try {
				GameSave save = new GameSave();
				save.createSave();
				out.writeObject(save);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean load(String filename) {
		Gdx.app.log("GameLoader", "Loading save...");
		GameSave save = loadSaveFile(filename);
		if (save == null) return false;
		
		Core.INSTANCE.story.setCurrentChapter(save.getCurrentChapter());
		Core.INSTANCE.story.setCurrentParagraph(save.getCurrentParagraph());
		Core.INSTANCE.story.setCurrentPage(save.getCurrentPage());
		Core.INSTANCE.missionSystem.initialize();

		if (save.isInBattle()) {
			HexMap map = HexMapGenerator.generateMap(save.getTiles());
			Core.INSTANCE.game.setMap(map);
			Core.INSTANCE.game.getPlayer().setName(save.getPlayerName());
			Core.INSTANCE.game.getPlayer().setID(save.getPlayerID());
			Core.INSTANCE.game.setPlayerInTurn(save.getPlayerInTurnID());
			Core.INSTANCE.actionSystem.setActionPoints(save.getActionPoints());

			for (EntityData entData : save.getEntities()) {
				Entity e = UnitFactory.createUnit(entData);
				Core.INSTANCE.ecs.engine.addEntity(e);
			}

			Core.INSTANCE.levelSystem.setCurrentLevel(save.getLevelID());
			Core.INSTANCE.levelSystem.enterCurrentLevel();
			Core.INSTANCE.screenSystem.setScreenToEnter(Screens.BATTLE);
			Core.INSTANCE.screenSystem.update();
		} else {
			Core.INSTANCE.screenSystem.setScreenToEnter(Screens.CAMPAIGN);
		}
		return true;
	}
	
	private static GameSave loadSaveFile(String filename) {
		GameSave save = null;
		try {
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			try {
				save = (GameSave) in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return save;
	}
}
