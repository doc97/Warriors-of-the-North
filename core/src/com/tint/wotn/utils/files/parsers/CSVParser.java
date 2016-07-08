package com.tint.wotn.utils.files.parsers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tint.wotn.utils.files.FileUtils;

/**
 * A {@link ConfigurationFileParser} for parsing data files in CSV format
 * @author doc97
 *
 */
public class CSVParser implements ConfigurationFileParser {

	@Override
	public Map<String, String> parse(File file) throws ConfigurationParserException {
		Map<String, String> data = new HashMap<String, String>();
		try {
			int fileHandle = FileUtils.openFile(file);
			List<String> lines = FileUtils.readLines(fileHandle);
			String[] properties = null;
			boolean firstLine = true;
			for (String line : lines) {
				if (line.matches("^\\s*#")) continue;
				String[] values = line.split("\\s*,\\s*");
				if (firstLine) {
					properties = new String[values.length];
					if (properties.length < 2) throw new ConfigurationParserException("File is not a CSV configuration file!");
					for (int i = 0; i < properties.length; i++)
						properties[i] = values[i];
					firstLine = false;
				} else {
					if (values.length != properties.length + 1) continue;
					String baseKey = values[0];
					for (int i = 1; i < properties.length; i++)
						data.put(baseKey + " " + properties[i - 1], values[i]);
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading configuration file: " + file.getName());
		}
		return data;
	}
}
