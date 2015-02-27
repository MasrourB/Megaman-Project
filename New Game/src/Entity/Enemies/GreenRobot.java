package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;
import Entity.Animation;
import Entity.Enemy;

/**
 * This green robot looks a little odd but I thought he'd be a cool addition.
 * Right now he's the only enemy in the game that moves around. If he hits a wall he'll just move in the opposite direction.
 * No plans to make him attack yet and I don't think I will in the future. He should stay a weak enemy (I know it's mean but forgive me.)
 * @author Masrour Basith
 *
 */
public class GreenRobot extends Enemy{
	private BufferedImage[] sprites;
	public GreenRobot(TileMap tm){
		super(tm);
		moveSpeed =1;
		maxSpeed  = 1;
		fallSpeed = 0.2;
		maxFallSpeed=10.0;
		
		width = 35;
		height=30;
		cwidth = 20;
		cheight = 20;
		
		health = maxHealth =10;
		damage =1;
		
		//load sprites
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/greenrobot.png"));
		sprites = new BufferedImage[3];
		for(int i = 0; i<sprites.length;i++){
			sprites[i] = spritesheet.getSubimage(i*width,0,width,height);
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		right = true;
		facingRight = true;
		animation.update();
	}
	
	/**
	 * Moving script for the enemy (It just walks around)
	 */
	private void getNextPosition(){
		if(left){
			dx -= moveSpeed;
			if(dx < -maxSpeed){
				dx = -maxSpeed;
			}
		}
		else if (right){
			dx += moveSpeed;
			if(dx > maxSpeed){
				dx = maxSpeed;
			}
		}
		if(falling){
			dy+=fallSpeed;
			
		}
	}
	/**
	 * Keeps updating the enemy's position
	 * Gives it collision with the ground, etc...
	 */
	public void update(){
		//update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer /1000000);
			if(elapsed > 400){
				flinching =false;
			}
		}
		//if it it hits a wall, go other direction
		if(right && dx ==0){
			right = false;
			left = true;
			facingRight = false;
		}
		else if(left && dx ==0){
			right = true;
			left = false;
			facingRight = true;
		}
		//update animation
		animation.update();
	}
	
	/**
	 * Draws a GreenRobot to the screen
	 */
	public void draw(Graphics2D g){
		//if (notOnScreen()) return;
		
		setMapPosition();
		super.draw(g);
	}
}
