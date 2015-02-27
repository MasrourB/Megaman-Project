package Entity;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 * A class for the explosion effect when the bullet projectile comes in contact with an enemy
 * @author Masrour Basith
 *
 */
public class Explosion {
	//Variables for coordinates
	private int x;
	private int y;
	private int xmap;
	private int ymap;
	
	//Variables for size of explosion
	private int width;
	private int height;
	private Animation animation;
	private BufferedImage[] sprites;
	
	private boolean remove; //Variable if we should remove an explosion
	
	/**
	 * Default Constructor
	 * @param x (X coordinate)
	 * @param y (Y coordinate)
	 */
	public Explosion(int x, int y){
		this.x=x;
		this.y=y;
		width = 30;
		height = 30;
		
		//Load in our explosion sprites
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/explosion.gif"));
			sprites = new BufferedImage[6];
			
			//Cookie cutter method again to iterate through the explosion sprite sheet
			for(int i = 0; i<sprites.length;i++){
				sprites[i] = spritesheet.getSubimage(i*width,0,width,height);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//Turn the explosion sprite sheet to an animation
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(70);
	}
	/**
	 * Update our explosion animation
	 */
	public void update(){
		animation.update();
		if(animation.hasPlayedOnce()){
			remove = true;
		}
	}
	/**
	 * Checks if we should remove our explosion animation (If it's played once)
	 * @return true if animation has played once or false if it hasn't
	 */
	public boolean shouldRemove(){
		return remove;
	}
	/**
	 * Sets the position of the explosion
	 * @param x (X Coordinate of the explosion)
	 * @param y (Y Coordinate of the explosion)
	 */
	public void setMapPosition(int x, int y){
		xmap = x;
		ymap = y;
		
	}
	/**
	 * Draw our explosion to the screen
	 * @param g Graphics object to be drawn
	 */
	public void draw(Graphics2D g){
		g.drawImage(animation.getImage(),x+xmap-width/2,y+ymap-height/2,null);
	}
}
