package GameState;
import java.awt.*;
import java.awt.event.KeyEvent;

import Audio.AudioPlayer;
import TileMap.Background;

/**
 * The menu state which gives the user the option to quit or go to level 1 (for now)
 * @author Masrour Basith
 *
 */
public class MenuState extends GameState{

	private Background bg;
	private int currentChoice = 0;
	private String[] options = {
			"Start","Help","Quit"
	};
	private Color titleColor;
	private Font titleFont;
	private Font font;
	private AudioPlayer bgMusic;
	public MenuState(GameStateManager gsm){
		this.gsm=gsm;
		try{
			bg = new Background("/Backgrounds/skyscraper.png",1);
			bg.setVector(-0.6,0);
			
			titleColor = new Color(0,0,255);
			titleFont = new Font("Mega Man Battle Network Regular", Font.PLAIN, 10);
			
			font = new Font("Mega Man Battle Network Regular", Font.PLAIN,8);
			//load the music
			bgMusic = new AudioPlayer("/Music/titleMusic.mp3");
			//play the music
			bgMusic.play();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//Leaving init() because if it isn't here then MenuState will be abstract
	@Override
	public void init(){
		
	}
	@Override
	public void update(){
		bg.update();
	}
	@Override
	public void draw(Graphics2D g){
		//draw g
		bg.draw(g);
		//System.out.println("Drawing...");
		
		//draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("MegaMan Simulator", 70, 70);
		
		//draw menu options
		g.setFont(font);
		for(int i = 0; i <options.length;i++){
			//White = currently selected option
			//Blue = Other options
			if(i == currentChoice){
				g.setColor(Color.WHITE);
			}
			else{
				g.setColor(Color.BLUE);
			}
			//This is here so that it seperates each option a decent amount
			//Makes things look nicer (Mostly due to i*25)
			g.drawString(options[i], 130, 140 + i *25);
		}
	}
	/**
	 * Method to decide which game state is to loaded on the main menu
	 */
	private void select(){
		if(currentChoice == 0){
			//start
			//Calls what's inside index 1 of the array list
			bgMusic.stop();
			//Loads level 1 by changing the state
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if(currentChoice == 1){
			//help
			//will get to this soon
		}
		if(currentChoice == 2){
			//exit
			System.exit(0);
		}
	}
	@Override
	public void keyPressed(int k){
		if(k == KeyEvent.VK_ENTER){
			//call the select method
			select();
		}
		//This allows for switching between options
		if(k == KeyEvent.VK_UP){
			currentChoice --;
			if(currentChoice == -1){
				currentChoice = options.length-1;
			}
		}
		if(k == KeyEvent.VK_DOWN){
			currentChoice++;
			if(currentChoice == options.length){
				currentChoice = 0;
			}
		}
	}
	
	@Override
	public void keyReleased(int k){
		
	}
	

}

