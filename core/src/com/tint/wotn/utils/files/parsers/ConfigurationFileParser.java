package com.tint.wotn.utils.files.parsers;

import java.io.File;
import java.util.Map;

/**
 * An interface for {@link ConfigurationFile} parser's
 * @see KeyValueParser
 * @see CSVParser
 * @author doc97
 *
 */
public interface ConfigurationFileParser {
	public Map<String, String> parse(File file) throws ConfigurationParserException;
}
