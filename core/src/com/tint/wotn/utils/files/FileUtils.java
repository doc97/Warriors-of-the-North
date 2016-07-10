package com.tint.wotn.utils.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling files
 * @author doc97
 */
public class FileUtils {

	private static List<BufferedReader> fileReaders = new ArrayList<BufferedReader>();
	private static List<BufferedWriter> fileWriters = new ArrayList<BufferedWriter>();
	private static List<Boolean> fileHandles = new ArrayList<Boolean>();
	private static final int MAX_FILES_OPEN = 16;
	
	public enum FileMode {
		READ,
		WRITE,
		READ_WRITE;
	}
	
	/**
	 * Opens a {@link BufferedReader}
	 * @param file The file to open a file handle for
	 * @param mode The mode to open the file in
	 * @return A ID that is used to access the assigned
	 * file handle ID
	 * @throws IOException If an I/O error occurs
	 * @see FileMode
	 */
	public static int openFile(File file, FileMode mode) throws IOException {
		FileReader fileReader = null;
		BufferedReader bufReader = null;
		FileWriter fileWriter = null;
		BufferedWriter bufWriter = null;

		if (mode == FileMode.READ || mode == FileMode.READ_WRITE) {
			fileReader = new FileReader(file);
			bufReader = new BufferedReader(fileReader);
			if (bufReader.markSupported()) bufReader.mark(0);
		}
		if (mode == FileMode.WRITE || mode == FileMode.READ_WRITE) {
			fileWriter = new FileWriter(file);
			bufWriter = new BufferedWriter(fileWriter);
		}
		return addFileHandle(bufReader, bufWriter);
	}
	
	/**
	 * Reads a line from a {@link File} by using a file handle ID
	 * @param fileHandleID The ID to the file handle of the file you want to read from
	 * @return A String containing the contents of the line, not including any
	 * line-termination characters, or null if the end of the stream has been reached
	 * @throws IOException If an I/O error occurs
	 */
	public static String readLine(int fileHandleID) throws IOException {
		if (!isFileReadable(fileHandleID)) return null;
		BufferedReader reader = fileReaders.get(fileHandleID);
		return reader.readLine();
	}
	
	/**
	 * Reads all lines from a {@link File} by using a file handle ID
	 * @param fileHandleID The ID to the file handle of the file you want to read from
	 * @return A {@code List} containing the lines
	 * @throws IOException If an I/O error occurs
	 */
	public static List<String> readLines(int fileHandleID) throws IOException {
		if (!isFileReadable(fileHandleID)) return null;
		resetReader(fileHandleID);
		BufferedReader reader = fileReaders.get(fileHandleID);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = reader.readLine()) != null) {
			lines.add(line);
		}
		return lines;
	}
	
	/**
	 * Resets the mark for the reader if file is opened for reading
	 * @param fileHandleID The ID to the file handle
	 * @throws IOException If an I/O error occurs
	 */
	public static void resetReader(int fileHandleID) throws IOException {
		if (!isFileReadable(fileHandleID)) return;
		BufferedReader reader = fileReaders.get(fileHandleID);
		if (reader.markSupported()) reader.reset();
	}
	
	/**
	 * Writes a {@code String} to the {@link BufferedWriter}
	 * @param text The text to write to the writer
	 * @param fileHandleID The ID of the file handle
	 * @throws IOException If an I/O error occurs
	 */
	public static void write(String text, int fileHandleID) throws IOException {
		if (!isFileWritable(fileHandleID)) return;
		BufferedWriter writer = fileWriters.get(fileHandleID);
		writer.write(text);
	}
	
	/**
	 * Writes a list of {@code String}'s to the {@link BufferedWriter}
	 * @param text The text to write to the writer
	 * @param fileHandleID The ID of the file handle
	 * @throws IOException If an I/O error occurs
	 */
	public static void write(List<String> texts, int fileHandleID) throws IOException {
		if (!isFileWritable(fileHandleID)) return;
		BufferedWriter writer = fileWriters.get(fileHandleID);
		for (String s : texts)
			writer.write(s);
	}
	
	/**
	 * Flushes the {@link BufferedWriter}
	 * @param fileHandleID The ID of the file handle
	 * @throws IOException If an I/O error occurs
	 */
	public static void flushWriter(int fileHandleID) throws IOException {
		if (!isFileWritable(fileHandleID)) return;
		BufferedWriter writer = fileWriters.get(fileHandleID);
		writer.flush();
	}
	
	/**
	 * Closes the {@link BufferedReader} and {@link BufferedWriter} assigned to
	 * the file handle ID and frees the file handle id for use
	 * @param fileHandleID The ID to the file handle of the file you want to close
	 * @throws IOException If an I/O error occurs
	 */
	public static void closeFile(int fileHandleID) throws IOException {
		if (isFileReadable(fileHandleID)) {
			fileReaders.get(fileHandleID).close();
			fileReaders.set(fileHandleID, null);
		}
		if (isFileWritable(fileHandleID)) {
			fileWriters.get(fileHandleID).close();
			fileWriters.set(fileHandleID, null);
		}
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
	 * @return Whether there exists a file handle with the ID
	 */
	public static boolean isFileOpen(int fileHandleID) {
		if (fileHandleID < 0 || fileHandleID >= fileHandles.size()) return false;
		return fileHandles.get(fileHandleID);
	}
	
	/**
	 * Checks if the file handle ID has been used to open a file in a readable mode
	 * @param fileHandleID The ID to the file handle you want to check
	 * @return Whether there is a {@link BufferedReaer} assigned to
	 * the file handle ID
	 */
	public static boolean isFileReadable(int fileHandleID) {
		if (!isFileOpen(fileHandleID)) return false;
		return fileReaders.get(fileHandleID) != null;
	}
	
	/**
	 * Checks if the file handle ID has been used ot open a file in a writable mode
	 * @param fileHandleID The ID to the file handle you want to check
	 * @return Wherther there is a {@link BufferedWriter} assigned to
	 * the file handle ID
	 */
	public static boolean isFileWritable(int fileHandleID) {
		if (!isFileOpen(fileHandleID)) return false;
		return fileWriters.get(fileHandleID) != null;
	}
	
	/**
	 * Returns the first free file handle id
	 * @return The file handle id or -1 if max
	 * capacity has been reached
	 * @see #MAX_FILES_OPEN
	 */
	private static int getFreeFileHandleID() {
		for (int i = 0; i < fileHandles.size(); i++) {
			if (fileHandles.get(i) == false) return i;
		}
		if (fileHandles.size() >= MAX_FILES_OPEN) return -1;
		return fileReaders.size();
	}
	
	/**
	 * Helper method for registering a new {@link BufferedReader}.
	 * @param reader The {@link BufferedReader} to register
	 * @param writer The {@link BufferedWriter} to register
	 * @return The new file handle ID, returns -1 if failed
	 */
	private static int addFileHandle(BufferedReader reader, BufferedWriter writer) {
		int fileHandleID = getFreeFileHandleID();
		if (fileHandleID == -1) return -1;
		if (fileHandleID < fileHandles.size()) {
			fileHandles.set(fileHandleID, true);
			fileReaders.set(fileHandleID, reader);
			fileWriters.set(fileHandleID, writer);
			return fileHandleID;
		} else {
			fileHandles.add(true);
			fileReaders.add(reader);
			fileWriters.add(writer);
			return fileHandleID;
		}
	}
}
