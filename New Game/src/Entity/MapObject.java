package Entity;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Main.GamePanel;
import TileMap.Tile;
import TileMap.TileMap;
/**
 * The most general entity of all in this game. Every object on the screen will inherit from this object.
 * @author Masrour  Basith
 *
 */
public abstract class MapObject {
	//tile things
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	//position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	//dimensions
	protected int width;
	protected int height;
	
	//collision box
	protected int cwidth;
	protected int cheight;
	
	//collision
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	//Four courners method for collision
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	//animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	
	//movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	
	//movement attributes
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	
	
	/**
	 * Default Constructor
	 * @param tm The tilemap for the MapObject
	 */
	public MapObject(TileMap tm){
		tileMap =tm;
		tileSize=tm.getTileSize();
	}
	/**
	 * Checks if a Mapobject has intersected with another MapObject
	 * @param o
	 * @return
	 */
	public boolean intersects(MapObject o){
		Rectangle r1 = this.getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
		
	}
	/**
	 * Gets the rectangle made by a MapObject
	 * @return The rectangle formed by the MapObject
	 */
	public Rectangle getRectangle(){
		return new Rectangle((int)x-cwidth,(int)y-cheight,cwidth,cheight);
	}
	
	
	/**
	 * Collision detection by the four corners of the rectangle
	 * @param x (X Coordinate of the MapObject)
	 * @param y (Y Coordinate of the MapObject)
	 */
	public void calculateCorners(double x, double y){
		int leftTile = (int)(x -cwidth/2)/tileSize;
		int rightTile = (int)(x+cwidth/2-1) / tileSize;
		int topTile = (int)(y-cheight/2)/tileSize;
		int bottomTile = (int)(y+cheight/2-1)/tileSize;
		
		//Fixes out of bounds expcetion
		if(topTile < 0 || bottomTile >= tileMap.getNumRows() ||
                leftTile < 0 || rightTile >= tileMap.getNumCols()) {
                topLeft = topRight = bottomLeft = bottomRight = false;
                return;
        }
		
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile,leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
		
	}
	/**
	 * Checks for collision with the tilemap. Without this the player would just fall endlessly.
	 */
	public void checkTileMapCollision(){
		currCol = (int)x/tileSize;
		currRow = (int)y/tileSize;
		xdest = x+dx;
		ydest = y+dy;
		
		xtemp=x;
		ytemp=y;
		
		calculateCorners(x,ydest);
		if(dy < 0){
			if(topLeft || topRight){
				dy =0;
				ytemp = currRow * tileSize +cheight/2;
			}
			else{
				ytemp+=dy;
			}
		}
		if(dy >0){
			if(bottomLeft || bottomRight){
				dy =0;
				falling = false;
				ytemp =(currRow +1)*tileSize -cheight/2;
			}
			else{
				ytemp += dy;
			}
		}
		calculateCorners(xdest,y);
		if(dx <0){
			if(topLeft || bottomLeft){
				dx = 0;
				xtemp = currCol * tileSize + cwidth/2;
			}
			else{
				xtemp += dx;
			}
		}
		if(dx > 0){
			if(topRight || bottomRight){
				dx =0;
				xtemp = (currCol +1)*tileSize -cwidth/2;
			}
			else{
				xtemp += dx;
			}
		}
		if(!falling){
			calculateCorners(x,ydest+1);
			if(!bottomLeft && !bottomRight){
				falling = true;
			}
		}
	}
	
	/**
	 * Gets x coordinate
	 * @return x coordinate
	 */
	public int getx(){
		return (int)x;
	}
	/**
	 * Gets y coordinate
	 * @return y coordinate
	 */
	public int gety(){
		return (int)y;
	}
	/**
	 * Gets width
	 * @return width
	 */
	public int getWidth(){
		return width;
	}
	/**
	 * Gets height
	 * @return height
	 */
	public int getHeight(){
		return height;
	}
	/**
	 * Gets width used for collision detection
	 * @return collision detection width
	 */
	public int getCWidth(){
		return cwidth;
	}
	/**
	 * Gets height used for collision detection
	 * @return collision detection height
	 */
	public int getCHeight(){
		return cheight;
	}
	
	/**
	 * global position
	 * @param x (X Coordinate)
	 * @param y (Y Coordinate)
	 */
	public void setPosition(double x, double y){
		this.x=x;
		this.y=y;
	}
	/**
	 * Sets the vector to where the MapObject moves
	 * @param dx Change in x
	 * @param dy Change in y
	 */
	public void setVector(double dx, double dy){
		this.dx=dx;
		this.dy = dy;
	}
	
	/**
	 * relative position
	 */
	public void setMapPosition(){
		xmap=tileMap.getx();
		ymap = tileMap.gety();
	}
	
	public void setLeft(boolean b){
		left = b;
	}
	public void setRight(boolean b){
		right =b;
	}
	public void setUp(boolean b){
		up = b;
	}
	public void setDown(boolean b){
		down = b;
	}
	public void setJumping(boolean b){
		jumping = b;
	}
	
	/**
	 * A method to see if a MapObject is on the screen or not
	 * @return
	 */
	public boolean notScreen(){
		return x+xmap + width <0|| x+xmap - width >GamePanel.WIDTH||
				y+ymap + height <0 || y+ymap - height > GamePanel.HEIGHT;
	}
	
	/**
	 * Draws a MapObject onto the screen
	 * @param g
	 */
	public void draw(java.awt.Graphics2D g){
		if(facingRight){
			g.drawImage(animation.getImage(),(int)(x+xmap-width/2),(int)(y+ymap-height/2),null);
		}
		else{
			g.drawImage(animation.getImage(), (int)(x+xmap - width /2+width), (int)(y+ymap - height /2), -width,height,null);
		}
	}
}
