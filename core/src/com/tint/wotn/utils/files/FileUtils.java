package com.tint.wotn.utils.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling files
 * @author doc97
 */
public class FileUtils {

	private static List<BufferedReader> fileHandles = new ArrayList<BufferedReader>();
	private static final int MAX_FILES_OPEN = 16;
	
	/**
	 * Opens a {@link BufferedReader}
	 * @param file The file to open a reader for
	 * @return A file handle ID that is used to access the
	 * assigned {@link BufferedReader}
	 * @throws IOException If an I/O error occurs
	 */
	public static int openFile(File file) throws IOException {
		FileReader fileReader = null;
		BufferedReader bufReader = null;
		fileReader = new FileReader(file);
		bufReader = new BufferedReader(fileReader);
		if (bufReader.markSupported()) bufReader.mark(0);
		return addFileHandle(bufReader);
	}
	
	/**
	 * Reads a line from a {@link File} by using a file handle ID
	 * @param fileHandleID The ID to the file handle of the file you want to read from
	 * @return A String containing the contents of the line, not including any
	 * line-termination characters, or null if the end of the stream has been reached
	 * @throws IOException If an I/O error occurs
	 */
	public static String readLine(int fileHandleID) throws IOException {
		if (!isFileOpen(fileHandleID)) return null;
		BufferedReader reader = fileHandles.get(fileHandleID);
		return reader.readLine();
	}
	
	/**
	 * Reads all lines from a {@link File} by using a file handle ID
	 * @param fileHandleID The ID to the file handle of the file you want to read from
	 * @return A {@code List} containing the lines
	 * @throws IOException If an I/O error occurs
	 */
	public static List<String> readLines(int fileHandleID) throws IOException {
		if (!isFileOpen(fileHandleID)) return null;
		resetFileHandle(fileHandleID);
		BufferedReader reader = fileHandles.get(fileHandleID);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = reader.readLine()) != null) {
			lines.add(line);
		}
		return lines;
	}
	
	public static void resetFileHandle(int fileHandleID) throws IOException {
		if (!isFileOpen(fileHandleID)) return;
		BufferedReader reader = fileHandles.get(fileHandleID);
		if (reader.markSupported()) reader.reset();
	}
	
	/**
	 * Closes the {@link BufferedReader} assigned to the file handle ID
	 * and frees the file handle id for use
	 * @param fileHandleID The ID to the file handle of the file you want to close
	 * @throws IOException If an I/O error occurs
	 */
	public static void closeFile(int fileHandleID) throws IOException {
		if (!isFileOpen(fileHandleID)) return;
		fileHandles.get(fileHandleID).close();
		fileHandles.set(fileHandleID, null);
	}
	
	/**
	 * Closes all open {@link BufferedReader}'s that has been opened with
	 * {@link #openFile(File)}
	 * @throws IOException
	 */
	public static void closeAllFiles() throws IOException {
		for (int i = 0; i < fileHandles.size(); i++)
			closeFile(i);
	}
	
	/**
	 * Checks if the file handle ID has been used to open a file
	 * @param fileHandleID The ID to the file handle you want to check
	 * @return Whether there is a {@link BufferedReader} assigned to
	 * the file handle ID
	 */
	public static boolean isFileOpen(int fileHandle) {
		if (fileHandle < 0 || fileHandle >= fileHandles.size()) return false;
		return fileHandles.get(fileHandle) != null;
	}
	
	/**
	 * Returns the first free file handle id
	 * @return The file handle id or -1 if max
	 * capacity has been reached
	 * @see #MAX_FILES_OPEN
	 */
	private static int getFreeFileHandleID() {
		for (int i = 0; i < fileHandles.size(); i++) {
			if (fileHandles.get(i) == null) return i;
		}
		if (fileHandles.size() >= MAX_FILES_OPEN) return -1;
		return fileHandles.size();
	}
	
	/**
	 * Helper method for registering a new {@link BufferedReader}.
	 * @param reader The {@link BufferedReader} to register
	 * @return The new file handle ID
	 */
	private static int addFileHandle(BufferedReader reader) {
		int fileHandleID = getFreeFileHandleID();
		if (fileHandleID < fileHandles.size()) {
			fileHandles.set(fileHandleID, reader);
			return fileHandleID;
		} else if (fileHandles.isEmpty()) {
			fileHandles.add(reader);
			return fileHandleID;
		} else if (fileHandleID == fileHandles.size()) {
			fileHandles.set(fileHandleID, reader);
			return fileHandleID;
		}
		return -1;
	}
}
