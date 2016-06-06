package com.tint.wotn.story;

import java.util.ArrayList;
import java.util.List;

public class Chapter {

	private List<Paragraph> paragraphs = new ArrayList<Paragraph>();
	
	public void addParagraph(Paragraph paragraph) {
		paragraphs.add(paragraph);
	}
	
	public Paragraph getParagraph(int index) {
		if (index < 0 || index >= paragraphs.size() || paragraphs.isEmpty()) return null;
		return paragraphs.get(index);
	}
	
	public int getParagraphCount() {
		return paragraphs.size();
	}
	
	public int getPageCount() {
		int pages = 0;
		for (Paragraph p : paragraphs) {
			pages += p.getPageCount();
		}
		return pages;
	}
}
