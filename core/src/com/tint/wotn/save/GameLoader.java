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
import com.tint.wotn.Core;
import com.tint.wotn.ecs.EntityData;
import com.tint.wotn.screens.Screens;
import com.tint.wotn.utils.UnitFactory;

public class GameLoader {

	public static boolean save(String filename) {
		System.out.println("Saving game...");
		try {
			File file = new File(filename);
			file.getParentFile().mkdirs();
			OutputStream fileStream = new FileOutputStream(file);
			OutputStream bufferStream = new BufferedOutputStream(fileStream);
			ObjectOutputStream out = new ObjectOutputStream(bufferStream);
			try {
				GameSave save = GameSave.createSave();
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
		GameSave save = loadSaveFile(filename);
		if (save == null) return false;
		
		if (save.isInBattle()) {
			Core.INSTANCE.screenSystem.setScreenToEnter(Screens.BATTLE);
			Core.INSTANCE.screenSystem.update();

			for (EntityData entData : save.getEntities()) {
				Entity e = UnitFactory.createUnit(entData);
				Core.INSTANCE.ecs.engine.addEntity(e);
			}
			
			Core.INSTANCE.levelSystem.enterCurrentLevel();
		} else {
			Core.INSTANCE.screenSystem.setScreenToEnter(Screens.CAMPAIGN);
		}
		
		Core.INSTANCE.missionSystem = save.getMissionSystem();
		Core.INSTANCE.game = save.getGame();
		Core.INSTANCE.actionSystem = save.getActionSystem();

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
