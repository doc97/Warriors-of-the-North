package com.tint.wotn.utils.files.parsers;

/**
 * A custom {@code Exception} for {@link ConfigurationFileParser}'s
 * @author doc97
 *
 */
public class ConfigurationParserException extends Exception {
	private static final long serialVersionUID = 6996942122627100714L;

	public ConfigurationParserException() {
		super();
	}
	
	public ConfigurationParserException(String message) {
		super(message);
	}
	
	public ConfigurationParserException(Throwable cause) {
		super(cause);
	}
	
	public ConfigurationParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
