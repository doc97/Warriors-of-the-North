package com.tint.wotn.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.tint.wotn.Core;
import com.tint.wotn.UnitType;
import com.tint.wotn.levels.maps.HexMap;

public class AssetLoader {

	public static void loadTexturesIntoGame() {
		HexMap.loadTextures();
		for(UnitType unitType : UnitType.values())
			unitType.loadTexture();
	}
	
	public static void loadTextures(String fileName) throws IOException {
		if(!checkPrerequisites(fileName, ".wotn_tex")) return;

		TextureParameter texLinearParam = new TextureParameter();
		texLinearParam.magFilter = TextureFilter.Linear;
		texLinearParam.minFilter = TextureFilter.Linear;
		texLinearParam.genMipMaps = true;

		String[] fileNames = parseAssetFile("configs/" + fileName);
		for(String file : fileNames)
			Core.INSTANCE.assetManager.load("textures/" + file, Texture.class, texLinearParam);			
	}
	
	private static boolean checkPrerequisites(String fileName, String targetExtension) {
		if(Core.INSTANCE.assetManager == null) {
			System.err.println("AssetManager not initialized");
			return false;
		}
		if(!fileName.endsWith(targetExtension)) {
			String[] fileNameParts = fileName.split(".");
			if(fileNameParts.length > 1) {
				String extension = fileNameParts[fileNameParts.length - 1];
				System.err.println("File type: " + extension + " is not supported");
			} else {
				System.err.println("File with extension not supported");
			}
			return false;
		}
		return true;
	}
	
	private static String[] parseAssetFile(String fileName) throws IOException {
		List<String> files = new ArrayList<String>();
		FileReader reader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(reader);
		
		
		String line;
		String comment1 = "//";
		String comment2 = "#";
		while((line = bufferedReader.readLine()) != null) {
			line.trim();
			if(line.startsWith(comment1) || line.startsWith(comment2)) continue;
			files.add(line);
		}
		reader.close();
		
		String[] a = new String[files.size()];
		a = files.toArray(a);
		return a;
	}
}
