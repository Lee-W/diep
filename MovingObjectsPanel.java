import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MovingObjectsPanel extends JPanel {
	
	final Dimension defaultDim;
	GameMap gm;

	private Timer t;

	public MovingObjectsPanel(Dimension dim) {
		defaultDim = dim;
		this.setPreferredSize(defaultDim);
		makeGameMap();

		setUpKeyMappings();
	}

	private void makeGameMap() {
		gm = new DiepIOMap(this.defaultDim);
		t = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//gm.tick();
				repaint();
			}

		});

		t.start();
	}

	private void setUpKeyMappings() {
		// maps keys with actions...
		//  The code below maps a KeyStroke to an action to be performed
		// In this case I mapped the space bar key to the action named "shoot"
		// Whenever someone hits the Space Bar the action shoot is sent out
		this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"shoot");

		this.getInputMap().put(KeyStroke.getKeyStroke("UP"),"moveUp");
		this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"),"moveDown");
		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"),"moveLeft");
		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),"moveRight");

		//  This associates the command shoot with some action.  In this
		// case, the action triggers a shoot command invoked on my GameMap.  In general, whatever
		// goes in the actionPerformed method will be executed when a shoot command
		// is sent...

		this.getActionMap().put("shoot",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gm.shoot();
			}
		});

		this.getActionMap().put("moveUp",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gm.move(3);
			}
		});

		this.getActionMap().put("moveDown",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gm.move(1);
			}
		});

		this.getActionMap().put("moveLeft",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gm.move(2);
			}
		});

		this.getActionMap().put("moveRight",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gm.move(0);
			}
		});

		this.requestFocusInWindow();
	}

	public void paintComponent(Graphics g){
		gm.draw(g);
	}
}
