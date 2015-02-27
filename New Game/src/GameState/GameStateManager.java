package GameState;
//import java.util.ArrayList;
//I decided not to use ArrayLists here but arrays instead because it cuts down on the loading times
/**
 * The game state manager for all game states. Stores them in an array and contains functions to manage them.
 * @author Masrour Basith
 *
 */
public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	public static final int NUMGAMESTATES =2;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public GameStateManager(){
		gameStates = new GameState[NUMGAMESTATES];
		currentState = MENUSTATE;
		//gameStates is an array list of all the states
		//Index 0=MENUSTATE, Index 1 = LEVEL1STATE
		loadState(currentState);
	}
	/**
	 * Loads the appropriate state depending if it's a menu state or a level 1 state
	 * more states to come
	 * @param state the state that we want to load
	 */
	private void loadState(int state){
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
	}
	/**
	 * Unloads the previous state
	 * @param state The state that we want to unload
	 */
	private void unloadState(int state){
		gameStates[state] = null;
	}
	/**
	 * Sets a state into gameStates
	 * @param state The state that we want to set
	 */
	public void setState(int state){
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		//Calls for the current state depending on the level
		//gameStates[currentState].init();
	}
	public void update(){
		//update the current state
		try{
		gameStates[currentState].update();
		}
		catch(Exception e){}
	}
	/**
	 * Draws to the screen
	 * @param g The graphic to be drawn
	 */
	public void draw(java.awt.Graphics2D g){
		try{
			gameStates[currentState].draw(g);
		}
		catch(Exception e){}
}
	/**
	 * Gives access to input from the keyboard
	 * @param k the key pressed as an integer
	 */
	public void keyPressed(int k){
	gameStates[currentState].keyPressed(k);
}
	/**
	 * Input if a key is released
	 * @param k the key released as an integer
	 */
	public void keyReleased(int k){
		gameStates[currentState].keyReleased(k);
	}
}
