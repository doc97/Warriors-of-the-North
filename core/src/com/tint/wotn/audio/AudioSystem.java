package com.tint.wotn.audio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.tint.wotn.Core;

public class AudioSystem {

	private LinkedList<AudioData> soundQueue;
	private LinkedList<AudioData> musicQueue;
	private List<Sound> soundInMemory;
	private List<Music> musicInMemory;
	
	public AudioSystem() {
		soundQueue = new LinkedList<AudioData>();
		musicQueue = new LinkedList<AudioData>();
		soundInMemory = new ArrayList<Sound>();
		musicInMemory = new ArrayList<Music>();
	}
	
	public void initialize() {
		soundQueue.clear();
		musicQueue.clear();
	}
	
	public void playSound(String filename, float volume, boolean loop) {
		AudioData data = new AudioData(filename, volume, loop);
		soundQueue.add(data);
	}
	
	public void playMusic(String filename, float volume, boolean loop) {
		AudioData data = new AudioData(filename, volume, loop);
		musicQueue.add(data);
	}
	
	public void update() {
		for (Iterator<Music> it = musicInMemory.iterator(); it.hasNext();) {
			Music music = it.next();
			if (!music.isPlaying()) {
				music.dispose();
				it.remove();
			}
		}
		
		if (!soundQueue.isEmpty()) {
			AudioData soundData = soundQueue.pop();
			Sound sound = Core.INSTANCE.assets.getSound(soundData.filename);
			if (sound != null) {
				long soundID = sound.play();
				sound.setVolume(soundID, soundData.volume);
				sound.setLooping(soundID, soundData.loop);
				
				if (!soundInMemory.contains(sound))
					soundInMemory.add(sound);
			}
		}
		
		if (!musicQueue.isEmpty()) {
			AudioData musicData = musicQueue.pop();
			Music music = Core.INSTANCE.assets.getMusic(musicData.filename);
			if (music != null) {
				music.setVolume(musicData.volume);
				music.setLooping(musicData.loop);
				music.play();
				
				if (!musicInMemory.contains(music))
					musicInMemory.add(music);
			}
		}
	}
	
	public void dispose() {
		for (Sound sound : soundInMemory) {
			sound.stop();
			sound.dispose();
		}
		soundInMemory.clear();
		
		for (Music music : musicInMemory) {
			music.stop();
			music.dispose();
		}
		musicInMemory.clear();
	}
	
	public class AudioData {
		public final String filename;
		public final float volume;
		public final boolean loop;
		
		public AudioData(String filename, float volume, boolean loop) {
			this.filename = filename;
			this.volume = volume;
			this.loop = loop;
		}
	}
}
