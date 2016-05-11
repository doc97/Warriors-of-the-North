package com.tint.wotn.audio;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.tint.wotn.Core;

public class AudioSystem {

	private LinkedList<AudioParams> soundQueue;
	private LinkedList<AudioParams> musicQueue;
	
	public AudioSystem() {
		soundQueue = new LinkedList<AudioParams>();
		musicQueue = new LinkedList<AudioParams>();
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
	
	public void update() {
		if (!soundQueue.isEmpty()) {
			AudioParams soundParams = soundQueue.pop();
			Sound sound = Core.INSTANCE.assets.getSound(soundParams.filename);
			if (sound != null) {
				long soundID = sound.play();
				sound.setVolume(soundID, soundParams.volume);
				sound.setLooping(soundID, soundParams.loop);
			}
		}
		
		if (!musicQueue.isEmpty()) {
			AudioParams musicParams = musicQueue.pop();
			Music music = Gdx.audio.newMusic(Gdx.files.internal(musicParams.filename));
			if (music != null) {
				music.setVolume(musicParams.volume);
				music.setLooping(musicParams.loop);
				music.play();
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
