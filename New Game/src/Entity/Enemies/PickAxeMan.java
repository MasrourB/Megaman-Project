package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import TileMap.TileMap;
import Entity.Animation;
import Entity.Enemy;
import Entity.Pickaxe;

/**
 * PickAxeMan similar to the Mettaur in that he doesn't move, but he throws pickaxes as his name implies.
 * @author Masrour Basith
 *
 */
public class PickAxeMan extends Enemy{

	private int numofAxes;
	private ArrayList<Pickaxe> pickaxes;
	private int pickaxeDamage=2;
	private BufferedImage[] sprites;
	public PickAxeMan(TileMap tm){
		super(tm);
		moveSpeed =1;
		maxSpeed  = 1;
		fallSpeed = 0.2;
		maxFallSpeed=10.0;
		
		width = 35;
		height=24;
		cwidth = 20;
		cheight = 20;
		
		health = maxHealth =20;
		damage =1;
		
		
		
		//load sprites
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/pickaxeman.png"));
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
		animation.setDelay(500);
		right = true;
		facingRight = true;
		animation.update();
	}
	
	/**
	 * Moving script for the enemy (It just walks around)
	 */
	private void getNextPosition(){
		if(falling){
			dy+=fallSpeed;
			
		}
	}
	public void checkAttack(ArrayList<Enemy> enemies){
		//loop thru enemies
		for (int i = 0; i <enemies.size(); i++){
			Enemy e = enemies.get(i);
			
			//fire balls
			for(int j = 0; j<pickaxes.size();j++){
				if(pickaxes.get(j).intersects(e)){
					e.hit(pickaxeDamage);
					pickaxes.get(j).setHit();
					break;
				}
			}
			//check for enemy collision
			if(intersects(e)){
				hit(e.getDamage());
			}
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
		//update animation
		animation.update();
	}
	
	/**
	 * Draws a PickAxeMan to the screen
	 */
	public void draw(Graphics2D g){
		//if (notOnScreen()) return;
		setMapPosition();
		super.draw(g);
	}
}
