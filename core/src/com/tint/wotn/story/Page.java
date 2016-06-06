package com.tint.wotn.story;

public class Page {

	public static final int MAX_LENGTH = 768;
	private String text = "";
	
	public String setText(String text) {
		if (text.length() > MAX_LENGTH) {
			this.text = text.substring(0, MAX_LENGTH);
			return text.substring(MAX_LENGTH);
		}
		this.text = text;
		return "";
	}
	
	public String addText(String text) {
		return setText(this.text + text);
	}
	
	public void clear() {
		text = "";
	}
	
	public String getText() {
		return text;
	}
	
	public boolean isFull() {
		return text.length() == MAX_LENGTH;
	}
}
