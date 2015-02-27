package Main;
import javax.swing.JFrame;
/**
 * The clas containing the main method for our game
 * @author Masrour Basith
 *
 */
public class Game {
	public static void main(String[] args) {
		JFrame window = new JFrame("MegaMan Simulator");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		
	}

}
