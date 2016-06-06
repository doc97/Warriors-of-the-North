package com.tint.wotn.story;

import java.util.ArrayList;
import java.util.List;

public class Paragraph {

	private List<Page> pages = new ArrayList<Page>();
	
	public void addText(String text) {
		Page currentPage = null;
		if (pages.isEmpty()) {
			currentPage = new Page();
			pages.add(currentPage);
		} else {
			currentPage = pages.get(pages.size() - 1);
		}

		while (!text.equals("")) {
			if (currentPage.isFull()) {
				currentPage = new Page();
				pages.add(currentPage);
			}
			text = currentPage.addText(text);
		}
	}
	
	public Page getPage(int index) {
		if (index < 0 || index >= pages.size() || pages.isEmpty()) return null;
		return pages.get(index);
	}
	
	public int getPageCount() {
		return pages.size();
	}
}
