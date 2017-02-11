import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;

import javax.swing.JFrame;

public class MovingObjectsGameLauncher {

	public static void main(String[] args) {
		JFrame gameFrame = new JFrame("Hello!");
		Map<String,String> environMap = System.getenv();

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		MovingObjectsPanel mop = new MovingObjectsPanel(d);

		gameFrame.setTitle("Oipe.id");
		gameFrame.add(mop);
		gameFrame.pack();
		gameFrame.setVisible(true);
		gameFrame.setDefaultCloseOperation(gameFrame.EXIT_ON_CLOSE);
		mop.repaint();
	}

}