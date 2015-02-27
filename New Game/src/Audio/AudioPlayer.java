package Audio;

import javax.sound.sampled.*;
/**
 * This is the AudioPlayer Class used to load music and play it
 * @author Masrour Basith 
 *
 */
public class AudioPlayer {
	private Clip clip;
	public AudioPlayer(String s){
		try{
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(),16,
					baseFormat.getChannels(),
					baseFormat.getChannels()*2,baseFormat.getSampleRate(),false);
		AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat,ais);
		clip  = AudioSystem.getClip();
		clip.open(dais);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		}
	/**
	 * Plays a clip
	 */
	public void play(){
		if(clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}
	/**
	 * Stops a clip
	 */
	public void stop(){
		if(clip.isRunning()) clip.stop();
	}
	/**
	 * Closes a clip
	 */
	public void close(){
		stop();
		clip.close();
	}
}

