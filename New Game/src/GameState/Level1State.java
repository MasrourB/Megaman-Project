package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


import Entity.*;
import Entity.Enemies.*;
import Main.GamePanel;
import TileMap.*;
import Audio.AudioPlayer;
/**
 * The state for level 1
 * It loads the level, player, enemies, and music
 * @author Masrour Basith
 *
 */
public class Level1State extends GameState{
	
	private TileMap tileMap;
	private Background bg;
	private Player player;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	//private HUD hud;
	private AudioPlayer bgMusic;
	
	public Level1State(GameStateManager gsm){
		this.gsm=gsm;
		init();
	}
	
	@Override
	public void init(){
		tileMap = new TileMap(30);
		//load the tiles
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		//load the map which tells where the tiles should go
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0,0);
	
		
		//load the background
		bg= new Background("/Backgrounds/skyscraper.png",0.1);
		player = new Player(tileMap);
		player.setPosition(100,100);
		
		populateEnemies();
		explosions = new ArrayList<Explosion>();
		
		//hud = new HUD(player);
		
		bgMusic = new AudioPlayer("/Music/levelMusic.mp3");
		bgMusic.play();
	}
	
	/**
	 * This function populates the map with enemies
	 */
	private void populateEnemies(){
		enemies = new ArrayList<Enemy>();
		GreenRobot g;
		Mettaur m;
		PickAxeMan p;
		//Populate the map with new GreenRobots
		Point[] greenRobotPoints = new Point[]{
			new Point(200,100),
			new Point(860,200),
			new Point(1525,200),
			new Point(2300,100),
			//new Point(3300,100)
		};
		//Iterates through the array
		for(int i=0;i<greenRobotPoints.length;i++){
			g = new GreenRobot(tileMap);
			g.setPosition(greenRobotPoints[i].x, greenRobotPoints[i].y);
			enemies.add(g);
		}
		//Mettaur
		Point[] mettaurPoints = new Point[]{
				new Point(380,100),
				new Point(700,100),
				new Point(1020,100)
		};
		for(int j =0; j<mettaurPoints.length;j++){
			m = new Mettaur(tileMap);
			m.setPosition(mettaurPoints[j].x, mettaurPoints[j].y);
			enemies.add(m);
		}
		//PickAxeMan
		Point[] pickAxeManPoints = new Point[]{
				new Point(3100,100)
		};
		for(int k =0;k<pickAxeManPoints.length;k++){
			p= new PickAxeMan(tileMap);
			p.setPosition(pickAxeManPoints[k].x, pickAxeManPoints[k].y);
			enemies.add(p);
		}
		
	}
	
	@Override
	public void update(){
		player.update();
		tileMap.setPosition(GamePanel.WIDTH/2 -player.getx(), GamePanel.HEIGHT/2 - player.gety());
	
		//set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		//attack enemies
		player.checkAttack(enemies);
		
	
		//update enemies
		for(int i = 0; i <enemies.size();i++){
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()){
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(),e.gety()));
			}
		}
		//update explosions
		for(int i = 0;i<explosions.size();i++){
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()){
				explosions.remove(i);
				i--;
			}
		}
		
	}

	@Override
	public void draw(Graphics2D g) {
		//draw bg
		bg.draw(g);
		
		//draw tile map
		tileMap.draw(g);
		
		//draw player
		player.draw(g);
		
		//draw enemies
		for(int i = 0; i <enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		
		
		//draw explosions
		for(int i = 0; i < explosions.size();i++){
			explosions.get(i).setMapPosition((int)tileMap.getx(),(int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		//draw hud
		//hud.draw(g);
		
	}

	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_SPACE) player.setJumping(true);
		if(k == KeyEvent.VK_E) player.setGliding(true);
		if(k == KeyEvent.VK_F) player.setFiring();
		
	}

	@Override
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_SPACE) player.setJumping(false);
		if(k == KeyEvent.VK_E) player.setGliding(false);
		
	}
	}
