import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class MovingObjectsPanel extends JPanel {
	
	final Dimension defaultDim;// = new Dimension(800,600);
	GameMap gm;

	private Timer t;
	
	public MovingObjectsPanel() {
		this(new Dimension(800,600));
	}
	public MovingObjectsPanel(Dimension dim) {
		defaultDim = dim;
		this.setPreferredSize(defaultDim);
		makeGameMap();
	}

	private void makeGameMap() {
		gm = new DiepIOMap(this.defaultDim);// let the map know what dim is
		t = new Timer(10, new ActionListener() {// fires off every 10 ms
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//gm.tick();// I tell the GameMap to tick... do what
				// you do every time the clock goes off.
				repaint();// naturally, we want to see the new view
			}

		});// this semicolon is here because it is the end of the new Timer construction...
	}

}
