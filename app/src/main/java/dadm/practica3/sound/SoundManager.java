package dadm.practica3.sound;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;
import java.util.HashMap;

public final class SoundManager {

	private static final int MAX_STREAMS = 10;
	private static final float DEFAULT_MUSIC_VOLUME = 0.6f;

	private HashMap<GameEvent, Integer> soundsMap;
	
	private Context context;
	private SoundPool soundPool;
	private MediaPlayer bgPlayer;
	private String currentSound;

	public SoundManager(Context context) {
		this.context = context;
		this.currentSound = "";
		loadSounds();
	}

	private void loadEventSound(Context context, GameEvent event, String... filename) {
		try {
			AssetFileDescriptor descriptor = context.getAssets().openFd("sfx/" + filename[0]);
			int soundId = soundPool.load(descriptor, 1);
			soundsMap.put(event, soundId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void playSoundForGameEvent(GameEvent event) {
		Integer soundId = soundsMap.get(event);
		if (soundId != null) {
			// Left Volume, Right Volume, priority (0 == lowest), loop (0 == no) and rate (1.0 normal playback rate)
			soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
		}
	}

	private void loadSounds() {
		createSoundPool();
		soundsMap = new HashMap<GameEvent, Integer>();
		loadEventSound(context, GameEvent.HitBird, "bird_sound.wav");
		loadEventSound(context, GameEvent.BulletFired, "bullet_shoot.mp3");
		loadEventSound(context, GameEvent.GameOver, "game_over.wav");
		loadEventSound(context, GameEvent.HitPlane, "plane_impact.wav");
		loadEventSound(context, GameEvent.TorpedoFired, "torpedo_shoot.wav");
	}

	public void loadMusic(String music) {
		if(music.equals(currentSound)){return;}
		try {
			currentSound=music;
			if(bgPlayer!=null){
				bgPlayer.stop();
			}
			bgPlayer=new MediaPlayer();
			// Important to not reuse it. It can be on a strange state
			AssetFileDescriptor afd = context.getAssets().openFd(currentSound);
			bgPlayer.setDataSource(afd.getFileDescriptor(),
					afd.getStartOffset(), afd.getLength());
			bgPlayer.setLooping(true);
			bgPlayer.setVolume(DEFAULT_MUSIC_VOLUME, DEFAULT_MUSIC_VOLUME);
			bgPlayer.prepare();
			bgPlayer.start();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createSoundPool() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
		}
		else {
			AudioAttributes audioAttributes = new AudioAttributes.Builder()
					.setUsage(AudioAttributes.USAGE_GAME)
					.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
					.build();
			soundPool = new SoundPool.Builder()
					.setAudioAttributes(audioAttributes)
					.setMaxStreams(MAX_STREAMS)
					.build();
		}
	}

	public void stopMusic(){
		unloadSounds();
		if(bgPlayer!=null){
			bgPlayer.stop();
		}
	}

	public void resumeMusic(){
		loadSounds();
		try {
			if(bgPlayer!=null){
				bgPlayer.stop();
			}
			bgPlayer=new MediaPlayer();
			// Important to not reuse it. It can be on a strange state
			AssetFileDescriptor afd = context.getAssets().openFd(currentSound);
			bgPlayer.setDataSource(afd.getFileDescriptor(),
					afd.getStartOffset(), afd.getLength());
			bgPlayer.setLooping(true);
			bgPlayer.setVolume(DEFAULT_MUSIC_VOLUME, DEFAULT_MUSIC_VOLUME);
			bgPlayer.prepare();
			bgPlayer.start();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void unloadSounds() {
		soundPool.release();
		soundPool = null;
		soundsMap.clear();		
	}

	private void unloadMusic() {
		bgPlayer.stop();
		bgPlayer.release();
	}

}
