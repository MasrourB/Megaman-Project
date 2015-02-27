package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;
import Entity.Animation;
import Entity.Enemy;

/**
 * The Mettaur is traditionally a pretty weak enemy in the Megaman series. 
 * He just pops in and out of his helmet shell for now.
 * Later on I want to make the Mettaur a lot more sophisticated as in making him invincible when he's in his shell and have him fire bullets when he's out of it.
 * @author Masrour Basith
 *
 */
public class Mettaur extends Enemy{

	
	
		private BufferedImage[] sprites;
		public Mettaur(TileMap tm){
			super(tm);
			moveSpeed =1;
			maxSpeed  = 1;
			fallSpeed = 0.2;
			maxFallSpeed=10.0;
			
			width = 19;
			height=15;
			cwidth = 15;
			cheight = 15;
			
			health = maxHealth =10;
			damage =1;
			
			//load sprites
			try{
				BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/mettaur.png"));
			sprites = new BufferedImage[2];
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
		 * Draws a mettaur on the screen
		 */
		public void draw(Graphics2D g){
			setMapPosition();
			super.draw(g);
		}
}

