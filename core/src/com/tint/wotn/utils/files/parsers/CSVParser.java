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
 * A {@link ConfigurationFileParser} for parsing data files in CSV format
 * @author doc97
 *
 */
public class CSVParser implements ConfigurationFileParser {

	@Override
	public void write(Map<String, String> data, File file) throws ConfigurationParserException {
		if (data == null) return;
		try {
			int fileHandle = FileUtils.openFile(file, FileMode.WRITE);
			try {
				List<String> propertiesProcessed = new ArrayList<String>();
				List<String> properties = new ArrayList<String>();
				
				String propertyLine = data.get("Properties");
				if (propertyLine == null) {
					Gdx.app.error("CSVParser", "Error in the format of the data in memory");
					return;
				}
				properties.add(propertyLine);
	
				String[] propertyArr = propertyLine.split("\\s*,\\s*");
				for (String key : data.keySet()) {
					String baseKey = key.split(" ")[0];
					if (propertiesProcessed.contains(baseKey)) continue;
					propertiesProcessed.add(baseKey);
					
					for (int i = 0; i < propertyArr.length; i++) {
						String property = propertyArr[i];
						String value = data.get(baseKey + " " + property);
						if (value == null) {
							FileUtils.closeFile(fileHandle);
							throw new ConfigurationParserException("Error in the format of the data in memory");
						}
						properties.add(value);
						if (i < propertyArr.length - 1)
							properties.add(",");
					}
					properties.add("\n");
				}
				
				FileUtils.write(properties, fileHandle);
			} catch (IOException e) {
				Gdx.app.error("CSVParser", "Error writing to configuration file: "
					+ file == null ? "null" : file.getName());
			} finally {
				FileUtils.closeFile(fileHandle);
			}
		} catch (IOException e) {
			Gdx.app.error("CSVParser", "Error opening configuration file: "
				+ file == null ? "null" : file.getName());
		}
	}
	@Override
	public Map<String, String> parse(File file) throws ConfigurationParserException {
		Map<String, String> data = new HashMap<String, String>();
		try {
			int fileHandle = FileUtils.openFile(file, FileMode.READ);
			try {
				List<String> lines = FileUtils.readLines(fileHandle);
				String[] properties = null;
				boolean firstLine = true;
				for (String line : lines) {
					if (line.trim().isEmpty()) continue;
					if (line.matches("^\\s*#")) continue;
					String[] values = line.split("\\s*,\\s*");
					if (firstLine) {
						properties = new String[values.length];
						if (properties.length < 2) throw new ConfigurationParserException("File is not a CSV configuration file!");
						for (int i = 0; i < properties.length; i++)
							properties[i] = values[i];
						data.put("Properties", line);
						firstLine = false;
					} else {
						if (values.length != properties.length + 1) continue;
						String baseKey = values[0];
						for (int i = 1; i < properties.length; i++)
							data.put(baseKey + " " + properties[i - 1], values[i]);
					}
				}
			} catch (IOException e) {
				Gdx.app.error("CSVParser", "Error reading configuration file: " + file == null ? "null" : file.getName());
			}
		} catch (IOException e) {
			Gdx.app.error("CSVParser", "Error opening configuration file: " + file == null ? "null" : file.getName());
		}
		return data;
	}
}
