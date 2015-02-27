package TileMap;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

import Main.GamePanel;

/**
 * Loads the image background
 * @author Masrour Basith
 *
 */
public class Background {

	private BufferedImage image;
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double moveScale;
	
	public Background(String s, double ms){
		try{
			image = ImageIO.read(getClass().getResourceAsStream(s));
			moveScale = ms;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * Sets the position of the background
	 * @param x (X Coordinate)
	 * @param y (Y Coordinate)
	 */
	public void setPosition(double x, double y){
		this.x=(x*moveScale) % GamePanel.WIDTH;
		this.y=(y* moveScale) % GamePanel.HEIGHT;
	}
	/**
	 * Sets a direction for the background to move
	 * @param dx Change in x
	 * @param dy Change in y
	 */
	public void setVector(double dx, double dy){
		this.dx=dx;
		this.dy=dy;
	}
	/**
	 * Scrolling update
	 */
	public void update(){
		x+=dx;
		y+=dy;
		setPosition(x,y);
	}
	/**
	 * Draw a background to the screen
	 * @param g The graphic to be drawn
	 */
	public void draw(Graphics2D g){
		g.drawImage(image, (int)x,(int)y, null);
		if(x <0){
			g.drawImage(image, (int)x + GamePanel.WIDTH,(int)y, null);
		}
		if(x > 0){
			g.drawImage(image,(int)x-GamePanel.WIDTH,(int)y,null);
		}
	}
	
}
