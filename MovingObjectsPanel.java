import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


public class MovingObjectsPanel extends JPanel {
	
	final Dimension defaultDim;// = new Dimension(800,600);
	GameMap gm;
	
	public MovingObjectsPanel() {
		this(new Dimension(800,600));
		makeGameMap();
	}
	public MovingObjectsPanel(Dimension dim) {
		defaultDim = dim;
		this.setPreferredSize(defaultDim);
		makeGameMap();
	}
	private void makeGameMap() {
		System.out.println(this.getPreferredSize().getHeight());
		System.out.println(this.defaultDim.getHeight());
		gm = new DiepIOMap(this.getPreferredSize());
		//this.add(gm);
	}
	public void paintComponent(Graphics g){
		gm.draw(g);
	}

}
