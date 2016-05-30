package com.tint.wotn.story;

import java.util.ArrayList;
import java.util.List;

public class StoryBook {

	private List<Chapter> chapters = new ArrayList<Chapter>();
	private int currentPage;
	
	public void setCurrentPage(int pageIndex) {
		if (pageIndex >= 0 && pageIndex < getPageCount())
			currentPage = pageIndex;
	}
	
	public void addChapter(Chapter chapter) {
		chapters.add(chapter);
	}
	
	public Page getPage(int pageIndex) {
		for (Chapter chapter: chapters) {
			if (pageIndex > chapter.getPageCount()) {
				pageIndex -= chapter.getPageCount();
				continue;
			}
			return chapter.getPage(pageIndex);
		}
		return null;
	}
	
	public Page getCurrentPage() {
		return getPage(currentPage);
	}

	public Chapter getChapterByNr(int chapterIndex) {
		if (chapterIndex < 0 || chapterIndex >= chapters.size() || chapters.isEmpty()) return null;
		return chapters.get(chapterIndex);
	}
	
	public Chapter getChapterByPage(int pageIndex) {
		for (Chapter chapter: chapters) {
			if (pageIndex > chapter.getPageCount()) {
				pageIndex -= chapter.getPageCount();
				continue;
			}
			return chapter;
		}
		return null;
	}
	
	public Chapter getCurrentChapter() {
		return getChapterByNr(currentPage);
	}
	
	public int getCurrentPageIndex() {
		return currentPage;
	}
	
	public int getPageCount() {
		int pages = 0;
		for (Chapter chapter : chapters) {
			pages += chapter.getPageCount();
		}
		return pages;
	}
}
