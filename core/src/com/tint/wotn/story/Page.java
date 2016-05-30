package com.tint.wotn.story;

public class Page {

	public static final int MAX_LENGTH = 256;
	private String text;
	
	public String setText(String text) {
		if (text.length() > MAX_LENGTH)
			this.text = text.substring(0, MAX_LENGTH);

		String excess = text.substring(MAX_LENGTH);
		return excess;
	}
	
	public String addText(String text) {
		if (text.length() + text.length() > MAX_LENGTH)
			this.text += text.substring(0, MAX_LENGTH - this.text.length());

		String excess = text.substring(MAX_LENGTH - this.text.length());
		return excess;
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
