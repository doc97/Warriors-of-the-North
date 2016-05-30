package com.tint.wotn.story;

import java.util.ArrayList;
import java.util.List;

public class Chapter {

	private List<Page> pages = new ArrayList<Page>();
	
	public void addText(String text) {
		Page currentPage = (pages.isEmpty() ? new Page() : pages.get(pages.size() - 1));
		while (!text.equals("")) {
			if (currentPage.isFull()) {
				currentPage = new Page();
				pages.add(currentPage);
			}
			text = currentPage.addText(text);
		}
	}
	
	public int getPageCount() {
		return pages.size();
	}

	public Page getPage(int index) {
		if (index < 0 || index >= pages.size() || pages.isEmpty()) return null;
		return pages.get(index);
	}
}
