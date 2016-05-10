package com.tint.wotn.ui;

import java.util.HashMap;
import java.util.Map;

public class UIDataStorage {

	private Map<String, Map<String, String>> storage = new HashMap<String, Map<String, String>>();
	
	public void addDataset(String key, Map<String, String> dataset) {
		storage.put(key, dataset);
	}
	
	/**
	 * Stores data into a hashmap structure
	 * @param datasetKey
	 * @param dataKey
	 * @param data Must not be null
	 */
	public void storeData(String datasetKey, String dataKey, String data) {
		if (data == null) return;
		if (!storage.containsKey(datasetKey))
			addDataset(datasetKey, new HashMap<String, String>());
		
		storage.get(datasetKey).put(dataKey, data);
	}
	
	public Map<String, String> getDataset(String datasetKey) {
		if (!storage.containsKey(datasetKey)) return null;
		return storage.get(datasetKey);
	}
	
	/**
	 * Gets the stored data
	 * @param datasetKey The key to the data set containing the data
	 * @param dataKey The key to the data in the data set
	 * @return Empty string if the data cannot be found
	 */
	public String getData(String datasetKey, String dataKey) {
		if (!storage.containsKey(datasetKey) || !storage.get(datasetKey).containsKey(dataKey)) return "";
		return storage.get(datasetKey).get(dataKey);
	}
}
