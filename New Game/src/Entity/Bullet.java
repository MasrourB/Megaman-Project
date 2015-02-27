package Entity;
import TileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
/**
 * The class for the bullet object
 * @author Masrour Basith
 *
 */
public class Bullet extends MapObject{
	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	
	/**
	 * Default Constructor
	 * @param tm The tilemap for the bullet
	 * @param right The direction the bullet is facing
	 */
	public Bullet(TileMap tm, boolean right){
		
		super(tm);
		facingRight = right; //Direction of the bullet is right by default
		moveSpeed = 3.8;
		
		//Moves the bullet in the direction it's facing
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;
		
		width = 30;
		height =30;
		cwidth= 14;
		cheight = 14;
		
		//load sprites
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/bullets.png"));
			sprites = new BufferedImage[4];
			//Cookie cutter method: Iterate though the sprite sheet with rectangles the size of the bullets
			//Then add them to the sprites frame array
			for(int i = 0; i<sprites.length; i++){
				sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
				
			}
			hitSprites = new BufferedImage[3];
			for(int i = 0; i<hitSprites.length;i++){
				hitSprites[i] = spritesheet.getSubimage(i*width,height,width,height);
			}
			//Turn this into an animation and set a delay
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * Makes the bullet has made contact with an enemy/player
	 */
	public void setHit(){
		if(hit) return;
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(70);
		dx =0;
	}
	/**
	 * Tells us if we should remove a sprite
	 * @return if we should remove a sprite or not (true or false)
	 */
	public boolean shouldRemove(){
		return remove;
	}
	/**
	 * Updates the map by checking for collision and animations
	 */
	public void update(){
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		
		//Checks if the projectile is stuck in a wall
		if(dx==0 && !hit){
			setHit();
		}
		
		animation.update();
		if(hit && animation.hasPlayedOnce()){
			remove = true;
		}
	}
	/**
	 * Draw our bullet to the screen
	 * @param g Graphics object to be drawn
	 */
	public void draw(Graphics2D g){
		setMapPosition();
		
		super.draw(g);
	}
	
}
