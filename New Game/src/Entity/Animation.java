package Entity;
import java.awt.image.BufferedImage;
/**
 * The Animation class object
 * @author Masrour Basith
 *
 */
public class Animation {
	private BufferedImage[] frames;
	private int currentFrame;
	
	private long startTime;
	private long delay;
	
	private boolean playedOnce;
	
	/**
	 * Default Constructor
	 */
	public Animation(){
		playedOnce = false;
	}
	/**
	 * Sets the frame for our array of frames to start playing from the top
	 * @param frames The array of frames we want to start
	 */
	public void setFrames(BufferedImage[] frames){
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime(); //Start the timer of our animation
		playedOnce = false;
	}
	/**
	 * Sets a delay for our animation
	 * @param d How long we want to set the delay in milliseconds
	 */
	public void setDelay(long d){
		delay = d;
	}
	/**
	 * Sets the frame to a specific one
	 * @param i The frame we want to set the animation to
	 */
	public void setFrame(int i){
		currentFrame = i;
	}
	/**
	 * Updates our animation
	 */
	public void update(){
		if(delay == -1) return;
		
		long elapsed = (System.nanoTime()-startTime) / 1000000;
		if(elapsed > delay){
			currentFrame++;
			startTime = System.nanoTime();
			
		}
		if(currentFrame == frames.length){
			currentFrame = 0;
			playedOnce = true;
		}
	}
	/**
	 * Gets the current frame of the animation
	 * @return the current frame
	 */
	public int getFrame(){
		return currentFrame;
	}
	/**
	 * Gets the image of the current frame
	 * @return the image of the current frame
	 */
	public BufferedImage getImage(){
		return frames[currentFrame];
	}
	/**
	 * Checks if an animation has played once
	 * @return
	 */
	public boolean hasPlayedOnce(){
		return playedOnce;
	}
}
