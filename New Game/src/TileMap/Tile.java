package TileMap;
import java.awt.image.BufferedImage;
/**
 * Tile Object class for our MapObjects to collide with
 * @author Masrour Basith
 *
 */
public class Tile {
	private BufferedImage image;
	private int type;
	
	//Our tiles come in two types or flavors: Normal or Blocked
	//Important when it comes to collision detection
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	/**
	 * Default constructor
	 * @param image Image of the tile
	 * @param type What type is it as in integer (Normal or blocked)
	 */
	public Tile(BufferedImage image,int type){
		this.image = image;
		this.type = type;
	}
	/**
	 * Get the image of the tile
	 * @return the image
	 */
	public BufferedImage getImage(){
		return image;
	}
	/**
	 * Get the type of the block
	 * @return the type
	 */
	public int getType(){
		return type;
	}
}
