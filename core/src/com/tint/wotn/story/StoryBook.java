package com.tint.wotn.story;

import java.util.ArrayList;
import java.util.List;

public class StoryBook {

	private List<Chapter> chapters = new ArrayList<Chapter>();
	private int currentChapter;
	private int currentParagraph;
	private int currentPage;
	
	public void initialize() {
		Chapter ch1 = new Chapter();
		Paragraph p1 = new Paragraph();
		p1.addText(
			"1 It was a rainy day but he could not have been more happy that day."
			+ " 2 It was a rainy day but he could not have been more happy that day."
			+ " 3 It was a rainy day but he could not have been more happy that day."
			+ " 4 It was a rainy day but he could not have been more happy that day."
			+ " 5 It was a rainy day but he could not have been more happy that day."
			+ " 6 It was a rainy day but he could not have been more happy that day."
			+ " 7 It was a rainy day but he could not have been more happy that day."
			+ " 8 It was a rainy day but he could not have been more happy that day."
			+ " 9 It was a rainy day but he could not have been more happy that day."
			+ " 10 It was a rainy day but he could not have been more happy that day."
			+ " 11 It was a rainy day but he could not have been more happy that day."
			+ " 12 It was a rainy day but he could not have been more happy that day."
			+ " 13 It was a rainy day but he could not have been more happy that day."
		);
		ch1.addParagraph(p1);
		addChapter(ch1);
	}
	
	public void addChapter(Chapter chapter) {
		chapters.add(chapter);
	}
	
	public boolean nextChapter() {
		return skipChapters(1);
	}
	
	public boolean previousChapter() {
		return skipChapters(-1);
	}
	
	public boolean skipChapters(int skip) {
		int newChapter = currentChapter + skip;
		if (newChapter < 0 || newChapter >= chapters.size()) return false;
		currentChapter = newChapter;
		return true;
	}
	
	public boolean nextParagraph() {
		return skipParagraphs(1);
	}
	
	public boolean previousParagraph() {
		return skipParagraphs(-1);
	}
	
	public boolean skipParagraphs(int skip) {
		int newParagraph = currentParagraph + skip;
		int toCurrentChapterEnd = getCurrentChapter().getParagraphCount() - newParagraph;

		while (toCurrentChapterEnd <= 0) {
			if (!nextChapter()) return false;
			toCurrentChapterEnd -= getCurrentChapter().getParagraphCount();
		}
		while (toCurrentChapterEnd > getCurrentChapter().getParagraphCount()) {
			if (!previousChapter()) return false;
			toCurrentChapterEnd += getCurrentChapter().getParagraphCount();
		}
		currentParagraph = toCurrentChapterEnd;
		return true;
	}
	
	public boolean nextPage() {
		return skipPages(1);
	}
	
	public boolean previousPage() {
		return skipPages(-1);
	}
	
	public boolean skipPages(int skip) {
		int newPage = currentPage + skip;
		int toCurrentParagraphEnd = getCurrentParagraph().getPageCount() - newPage;

		while (toCurrentParagraphEnd <= 0) {
			if (!nextParagraph()) return false;
			toCurrentParagraphEnd -= getCurrentParagraph().getPageCount();
		}
		while (toCurrentParagraphEnd > getCurrentParagraph().getPageCount()) {
			if (!previousParagraph()) return false;
			toCurrentParagraphEnd += getCurrentParagraph().getPageCount();
		}
		currentPage = toCurrentParagraphEnd;
		return true;
	}
	
	public void setCurrentChapter(int chapterIndex) {
		if (chapterIndex < 0 || chapterIndex >= chapters.size()) return;
		currentChapter = chapterIndex;
	}
	
	public void setCurrentParagraph(int paragraphIndex) {
		if (paragraphIndex < 0 || paragraphIndex >= getCurrentParagraph().getPageCount()) return;
		currentParagraph = paragraphIndex;
	}
	public void setCurrentPage(int pageIndex) {
		if (pageIndex < 0 && pageIndex >= getCurrentParagraph().getPageCount()) return;
		currentPage = pageIndex;
	}
		
	public Chapter getChapter(int chapterIndex) {
		if (chapterIndex < 0 || chapterIndex >= chapters.size()) return null;
		return chapters.get(chapterIndex);
	}
	
	public Paragraph getParagraph(int chapterIndex, int paragraphIndex) {
		Chapter chapter = getChapter(chapterIndex);
		if (chapter == null) return null;
		if (paragraphIndex < 0 || paragraphIndex >= chapter.getParagraphCount()) return null;
		return chapter.getParagraph(paragraphIndex);
	}
	
	public Page getPage(int chapterIndex, int paragraphIndex, int pageIndex) {
		Paragraph paragraph = getParagraph(chapterIndex, paragraphIndex);
		if (paragraph == null) return null;
		if (pageIndex < 0 || pageIndex >= paragraph.getPageCount()) return null;
		return paragraph.getPage(pageIndex);
	}
	
	public Chapter getCurrentChapter() {
		return getChapter(currentChapter);
	}
	
	public Paragraph getCurrentParagraph() {
		return getParagraph(currentChapter, currentParagraph);
	}
	
	public Page getCurrentPage() {
		return getPage(currentChapter, currentParagraph, currentPage);
	}
	
	public int getCurrentChapterIndex() {
		return currentChapter;
	}
	
	public int getCurrentParagraphIndex() {
		return currentParagraph;
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
