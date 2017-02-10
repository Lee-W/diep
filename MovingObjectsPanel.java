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

		setUpKeyMappings();
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
				System.out.println("shoot.");
				//gm.shoot();
			}
		});

		this.getActionMap().put("moveUp",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("move up.");
				//gm.shoot();
			}
		});

		this.getActionMap().put("moveDown",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("move down.");
				//gm.shoot();
			}
		});

		this.getActionMap().put("moveLeft",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("move left.");
				//gm.shoot();
			}
		});

		this.getActionMap().put("moveRight",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("move right.");
				//gm.shoot();
			}
		});

		this.requestFocusInWindow();
	}

}
