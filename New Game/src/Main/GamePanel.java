package Main;
import java.awt.*;
import java.awt.event.*;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameState.GameStateManager;

/**
 * Game panel object which is the screen for our game (The heartbeat basically)
 * @author Masrour Basith
 *
 */
public class GamePanel extends JPanel implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	public static final int WIDTH=320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000/FPS;
	
	//image
	private BufferedImage image;
	private Graphics2D g;
	
	//game state manager
	private GameStateManager gsm;
	
	/**
	 * Default Constructor
	 */
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		setFocusable(true);
		requestFocus();
	}
	/**
	 * Start the game
	 */
	public void addNotify(){
		super.addNotify();
		if(thread==null){
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	/**
	 * Initialize the game
	 */
	private void init(){
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		
		gsm = new GameStateManager();
	}
	/**
	 * Run the game
	 */
	public void run(){
		init();
		long start;
		long elapsed;
		long wait;
		//game loop
		while(running){
			start = System.nanoTime();
			update();
			draw();
			drawToScreen();
			elapsed = System.nanoTime()-start;
			wait = targetTime - elapsed/1000000;
			
			if(wait <0){
				wait = 5;
			}
			try{
				Thread.sleep(wait);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * Update the game panel
	 */
	private void update(){
		gsm.update();
	}
	/**
	 * Draw a game panel
	 */
	private void draw(){
		gsm.draw(g);
	}
	/**
	 * Draw onto the game panel
	 */
	private void drawToScreen(){
		Graphics g2 = getGraphics();
		g2.drawImage(image,0,0,WIDTH*SCALE, HEIGHT*SCALE,	null);
		g2.dispose();
	}
	public void keyTyped(KeyEvent key){
		gsm.keyPressed(key.getKeyCode());
	}
	public void keyPressed(KeyEvent key){
		gsm.keyPressed(key.getKeyCode());
	}
	public void keyReleased(KeyEvent key){
		gsm.keyReleased(key.getKeyCode());
	}
}
