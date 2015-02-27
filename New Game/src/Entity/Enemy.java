package Entity;

import TileMap.TileMap;;

/**
 * The enemy class which helps determine if it's been hit or is dead
 * @author mazdu_000
 *
 */
public class Enemy extends MapObject{
	
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	
	protected boolean flinching;
	protected long flinchTimer;
	
	
	/**
	 * Calls the super function
	 * @param tm
	 */
	public Enemy(TileMap tm){
		super(tm);
	}
	
	/**
	 * Finds out if an enemy is dead
	 * @return true if the enemy is dead and false if not
	 */
	public boolean isDead(){
		return dead;
	}
	
	/**
	 * Finds out the damage on an enemy
	 * @return Amount of damage
	 */
	public int getDamage(){
		return damage;
	}
public void update(){
		
	}
	/**
	 * Where the damage calculations happen
	 * @param damage Amount of damage taken
	 */
	public void hit(int damage){
		if(dead||flinching) return;
		health -= damage;
		if(health <0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
}
