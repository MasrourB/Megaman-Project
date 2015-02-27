package Entity;
import Audio.AudioPlayer;
import TileMap.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
/**
 * The player class which defines all the characteristics of the player
 * @author Masrour Basith
 *
 */
public class Player extends MapObject{
	// player things
	private int health;
	private int maxHealth;
	private int fire;
	private int maxFire;
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;
	
	//attacks
	private boolean firing;
	//private int fireCost;
	private int bulletDamage;
	private ArrayList<Bullet> bullets;
	
	
	
	//gliding
	private boolean gliding;
	
	//animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			2,3,1,1,1,1,1,2,2
	};
	
	//animation actions
	private static final int IDLE = 0;
	private static final int WALKING =1;
	private static final int JUMPING =2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	private static final int FIRING = 5;
	private static final int FLINCHING = 6;
	
	
	//Hashmap where String is the key and audioplayer is the value
	private HashMap<String,AudioPlayer> sfx;

	public Player(TileMap tm){
		super(tm);
		
		width = 24;
		height = 24;
		cwidth = 20;
		cheight = 20;
		
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
		health  = maxHealth =5;
		fire = maxFire = 2500;
		
		//fireCost = 200;
		bulletDamage =2;
		bullets = new ArrayList<Bullet>();
		
		
		
		//load sprites
		try{
			//loads the sprite sheet
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/megaupdate.png"));
			sprites = new ArrayList<BufferedImage[]>();
			
			//Kind of like cuts out the sprites in a cookie cutter fashion
			//Except the cookies are rectangles
			for(int i = 0; i <8; i++){
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
			for(int j = 0; j < numFrames[i]; j++){
				
				if(i !=2 && i != 5){
				bi[j]= spritesheet.getSubimage(j*width,i*height,width,height);
			}
				//These else if statements change the dimensions of the rectangle cookie cutters to match the sprites
				//Megaman's jumping sprite is 6 pixels taller than his idle stance
				else if(i ==2){
					bi[j]= spritesheet.getSubimage(j*width,i*height+6,width,height);
					}
				//Megaman's shooting sprite is 5 pixels wider than his idle stance
				else if(i == 5){
					bi[j]= spritesheet.getSubimage(j*width,i*height,width+5,height);
				}
			}
			sprites.add(bi);
			}
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//update the animation
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		
		//creates a new hash map
		sfx = new HashMap<String,AudioPlayer>();
		//jump = key
		//Sound Effect = value
		sfx.put("jump", new AudioPlayer("/SFX/jump.mp3"));
		
	}//End player class
	public int getHealth(){
		return health;
	}
	public int getMaxHealth(){
		return maxHealth;
	}
	public int getFire(){
		return fire;
	}
	public int getMaxFire(){
		return maxFire;
	}
	public void setFiring(){
		firing = true;
	}
	public boolean isFlinching(){
		return flinching;
	}
	public void setGliding(boolean b){
		gliding = b;
	}
	
	/**
	 * Check if an attack has hit an enemy
	 * @param enemies The enemies we check if an attack has made contact with
	 */
	public void checkAttack(ArrayList<Enemy> enemies){
		//loop thru enemies
		for (int i = 0; i <enemies.size(); i++){
			Enemy e = enemies.get(i);
			
			//fire balls
			for(int j = 0; j<bullets.size();j++){
				if(bullets.get(j).intersects(e)){
					e.hit(bulletDamage);
					bullets.get(j).setHit();
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
	 * Reduces health of the player and forces it to flinch
	 * @param damage
	 */
	public void hit(int damage){
		if(flinching) return;
		health -= damage;
		if(health <0) health =0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	/**
	 * Gets the next position of the player
	 */
	private void getNextPosition(){
		//movement physics
		//gives off an acceleration feel
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
		else{
			if(dx > 0){
				dx -= stopSpeed;
				if(dx <0){
					dx =0;
				}
			}
			else if(dx <0){
				dx += stopSpeed;
				if(dx >0){
					dx = 0;
				}
			}
		}
		//cannot move while attacking except when in air
		//might take this out I don't know
		if((currentAction == FIRING) && !(jumping || falling)){
			dx = 0;
		}
		//jumping
		if(jumping && !falling){
			sfx.get("jump").play();
			dy = jumpStart;
			falling = true;
		}
		//falling
		if(falling){
			if(dy > 0 && gliding) dy += fallSpeed *.1;
			else dy += fallSpeed;
			
			if(dy >0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
	}
	
	/**
	 * Updates the posistion of the player
	 */
	public void update(){
		//update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		
		
		
		//check if the animation has stopped
				
				if(currentAction == FIRING){
					if(animation.hasPlayedOnce())firing = false;
				}
				
				//attack
				//fire+=1;
				if(fire >maxFire) fire = maxFire;
				if(firing && currentAction != FIRING){
					
						
						Bullet bullet = new Bullet(tileMap, facingRight);
						bullet.setPosition(x,y);
						bullets.add(bullet);
					
				}
				//update shots
				for(int i =0; i <bullets.size();i++){
					bullets.get(i).update();
					if(bullets.get(i).shouldRemove()){
						bullets.remove(i);
						i--;
					}
				}
		
			//check finish flinching
				if(flinching){
					currentAction = FLINCHING;
					animation.setFrames(sprites.get(FLINCHING));
					animation.setDelay(300);
					width=24;
					long elapsed = (System.nanoTime() - flinchTimer)/1000000;
					
					if(elapsed > 1000){
						flinching = false;
					}
				}
				
		//set animation
		
		if(firing){
			if(currentAction != FIRING){
				currentAction = FIRING;
				animation.setFrames(sprites.get(FIRING));
				animation.setDelay(100);
				width = 24;
			}
		}
		else if (dy >0){
			if(gliding){
				if(currentAction != GLIDING){
					currentAction = GLIDING;
					animation.setFrames(sprites.get(JUMPING));
					animation.setDelay(100);
					width = 24;
					
				}
			}
			
			else if(currentAction != FALLING){
				currentAction = FALLING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(100);
				width = 24;
			}
		}
		else if(dy <0){
			if(currentAction!=JUMPING){
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 24;
			}
		}
		else if (left || right){
			if (currentAction!=WALKING){
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(100);
				width=24;
			}
		}
		else{
			if(currentAction!= IDLE){
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 24;
			}
		}
		animation.update();
		
		//set direction
		if(currentAction != FIRING){
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
	}
	
	/**
	 * Draws a player to the screen
	 */
	public void draw(Graphics2D g){
		setMapPosition();
		
		for(int i = 0; i<bullets.size();i++){
			bullets.get(i).draw(g);
		}
		//draw player
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) /1000000;
			if(elapsed/100% 2 ==0){
				return;
			}
		}
		super.draw(g);
	}
}
