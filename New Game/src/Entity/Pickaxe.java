package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

/**
 * This is basically the same thing as the Bullet class but the sprites loaded are the pickaxe sprites instead of bullets.
 * @author Masrour Basith
 *
 */
public class Pickaxe extends MapObject{

	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	public Pickaxe(TileMap tm, boolean right){
		
		super(tm);
		facingRight = right;
		moveSpeed = 3.8;
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;
		
		width = 30;
		height =30;
		cwidth= 14;
		cheight = 14;
		
		//load sprites
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/pickaxe.png"));
			sprites = new BufferedImage[4];
			for(int i = 0; i<sprites.length; i++){
				sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
				
			}
			hitSprites = new BufferedImage[3];
			for(int i = 0; i<hitSprites.length;i++){
				hitSprites[i] = spritesheet.getSubimage(i*width,height,width,height);
			}
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 
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
	 * updates the map by checking for collision and animations
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
	public void draw(Graphics2D g){
		setMapPosition();
		
		super.draw(g);
	}
	
}
