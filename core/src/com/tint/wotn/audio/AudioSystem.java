package com.tint.wotn.audio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.tint.wotn.Core;

public class AudioSystem {

	private LinkedList<AudioParams> soundQueue;
	private LinkedList<AudioParams> musicQueue;
	private Map<String, List<Long>> currentSounds;
	private List<String> currentMusic;
	
	public AudioSystem() {
		soundQueue = new LinkedList<AudioParams>();
		musicQueue = new LinkedList<AudioParams>();
		currentSounds = new HashMap<String, List<Long>>();
		currentMusic = new ArrayList<String>();
	}
	
	public void initialize() {
		soundQueue.clear();
		musicQueue.clear();
	}
	
	public void playSound(String filename, float volume, boolean loop) {
		AudioParams params = new AudioParams(filename, volume, loop);
		soundQueue.add(params);
	}
	
	
	public void playMusic(String filename, float volume, boolean loop) {
		AudioParams params = new AudioParams(filename, volume, loop);
		musicQueue.add(params);
	}
	
	public void stopSound(String filename) {
		Core.INSTANCE.assets.getSound(filename).stop();
	}
	
	public void stopMusic(String filename) {
		Core.INSTANCE.assets.getMusic(filename).stop();
	}
	
	public void stopAllSounds() {
		for (String name : currentSounds.keySet())
			Core.INSTANCE.assets.getSound(name).stop();
		currentSounds.clear();
	}
	
	public void stopAllMusic() {
		for (String name : currentMusic)
			Core.INSTANCE.assets.getMusic(name).stop();
		currentMusic.clear();
	}
	
	public void update() {
		if (!soundQueue.isEmpty()) {
			AudioParams soundParams = soundQueue.pop();
			Sound sound = Core.INSTANCE.assets.getSound(soundParams.filename);
			if (sound != null) {
				long soundID = sound.play();
				sound.setVolume(soundID, soundParams.volume);
				sound.setLooping(soundID, soundParams.loop);
				
				if (!currentSounds.containsKey(soundParams.filename))
					currentSounds.put(soundParams.filename, new ArrayList<Long>());

				currentSounds.get(soundParams.filename).add(soundID);
			}
		}
		
		if (!musicQueue.isEmpty()) {
			AudioParams musicParams = musicQueue.pop();
			Music music = Gdx.audio.newMusic(Gdx.files.internal(musicParams.filename));
			if (music != null) {
				music.setVolume(musicParams.volume);
				music.setLooping(musicParams.loop);
				music.play();
				
				currentMusic.add(musicParams.filename);
			}
		}
	}
	
	public class AudioParams {
		public final String filename;
		public final float volume;
		public final boolean loop;
		
		public AudioParams(String filename, float volume, boolean loop) {
			this.filename = filename;
			this.volume = volume;
			this.loop = loop;
		}
	}
}
