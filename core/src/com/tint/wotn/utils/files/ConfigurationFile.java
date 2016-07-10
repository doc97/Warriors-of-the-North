package com.tint.wotn.utils.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import com.tint.wotn.utils.files.parsers.ConfigurationFileParser;
import com.tint.wotn.utils.files.parsers.ConfigurationParserException;
import com.tint.wotn.utils.files.parsers.KeyValueParser;

/**
 * A class that makes it easy to parse and query data from configuration file's by using different {@code ConfigurationFileParser}'s
 * @see ConfigurationFileParser
 * @author doc97
 *
 */
public class ConfigurationFile {

	private File file;
	private ConfigurationFileParser parser;
	private Map<String, String> data;

	public ConfigurationFile(File file) throws FileNotFoundException {
		this(file, new KeyValueParser());
	}
	
	public ConfigurationFile(File file, ConfigurationFileParser parser) throws FileNotFoundException {
		this.file = file;
		if (file == null) throw new NullPointerException();
		if (!file.exists()) throw new FileNotFoundException(file.getName());
		this.parser = parser;
		data = new HashMap<String, String>();
	}
	
	public void parseFile() throws ConfigurationParserException {
		if (file == null) return;
		data = parser.parse(file);
	}
	
	public void saveFile() throws ConfigurationParserException {
		saveFile(file);
	}
	
	public void saveFile(File file) throws ConfigurationParserException {
		if (file == null) return;
		parser.write(data, file);
	}

	public void setData(Map<String, String> data) {
		if (data == null) return;
		this.data = data;
	}

	public String getValue(String key) {
		if (hasKey(key)) return data.get(key);
		return null;
	}
	
	public String getFilename() {
		return file.getName();
	}
	
	public String getAbsolutePath() {
		return file.getAbsolutePath();
	}
	
	public boolean hasKey(String key) {
		if (key == null) return false;
		return data.containsKey(key);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (String key : data.keySet())
			builder.append(key).append(" : ").append(data.get(key)).append("\n");
		return builder.toString();
	}
}
