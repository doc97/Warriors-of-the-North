package com.tint.wotn.utils.files.parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.tint.wotn.utils.files.FileUtils;
import com.tint.wotn.utils.files.FileUtils.FileMode;

/**
 * A {@link ConfigurationFileParser} for parsing key-value data files
 * @author doc97
 *
 */
public class KeyValueParser implements ConfigurationFileParser {
	
	@Override
	public void write(Map<String, String> data, File file) {
		if (data == null) return;
		try {
			int fileHandle = FileUtils.openFile(file, FileMode.WRITE);
			try {
				List<String> properties = new ArrayList<String>();
				for (String key : data.keySet()) {
					properties.add(key);
					properties.add("=");
					properties.add(data.get(key));
					properties.add("\n");
				}
				FileUtils.write(properties, fileHandle);
			} catch (IOException e) {
				Gdx.app.error("KeyValueParser", "Error writing to configuration file: " + file == null ? "null" : file.getName());
			} finally {
				FileUtils.closeFile(fileHandle);
			}
		} catch (IOException e) {
			Gdx.app.error("KeyValueParser", "Error opening configuration file: " + file == null ? "null" : file.getName());
		}
	}
	
	@Override
	public Map<String, String> parse(File file) throws ConfigurationParserException {
		Map<String, String> data = new HashMap<String, String>();
		try {
			int fileHandle = FileUtils.openFile(file, FileMode.READ);
			try {
				List<String> lines = FileUtils.readLines(fileHandle);
				for (String line : lines) {
					if (line.trim().isEmpty()) continue;
					if (line.matches("^\\s*#")) continue;
					if (line.matches(".+=.*")) {
						String[] lineData = line.split("=");
						String key = lineData[0].trim();
						String value = "";
						if (lineData.length > 1)
							value = lineData[1].trim();

						data.put(key, value);
					} else {
						FileUtils.closeFile(fileHandle);
						throw new ConfigurationParserException("File is not a key-value configuration file!");
					}
				}
			} catch (IOException e) {
				Gdx.app.error("KeyValueParser", "Error reading configuration file: " + file == null ? "null" : file.getName());
			} finally {
				FileUtils.closeFile(fileHandle);
			}
		} catch (IOException e) {
			Gdx.app.error("KeyValueParser", "Error opening configuration file: " + file == null ? "null" : file.getName());
		}
		return data;
	}
}
