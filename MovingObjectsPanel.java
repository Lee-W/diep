import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MovingObjectsPanel extends JPanel {
	
	final Dimension defaultDim;
	GameMap gm;

	private double lastShot = System.currentTimeMillis();

	private Timer t;

	private int mouseX = 0;
	private int mouseY = 0;

	public MovingObjectsPanel(Dimension dim) {
		defaultDim = dim;
		this.setPreferredSize(defaultDim);
		makeGameMap();

		setUpMotionListener();
		setUpKeyMappings();
	}

	private void makeGameMap() {
		gm = new DiepIOMap(this.defaultDim);
		t = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}

		});

		t.start();

		Timer aiTank = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((DiepIOMap) gm).aiShoot();
			}
		});

		aiTank.start();
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

		this.getInputMap().put(KeyStroke.getKeyStroke("W"),"moveUp");
		this.getInputMap().put(KeyStroke.getKeyStroke("S"),"moveDown");
		this.getInputMap().put(KeyStroke.getKeyStroke("A"),"moveLeft");
		this.getInputMap().put(KeyStroke.getKeyStroke("D"),"moveRight");

		//  This associates the command shoot with some action.  In this
		// case, the action triggers a shoot command invoked on my GameMap.  In general, whatever
		// goes in the actionPerformed method will be executed when a shoot command
		// is sent...

		this.getActionMap().put("shoot",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if ((System.currentTimeMillis() - lastShot)>150){
					lastShot = System.currentTimeMillis();
					gm.shoot();
				}
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

	private void setUpMotionListener(){
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				mouseX = arg0.getX();
				mouseY = arg0.getY();
				((DiepIOMap) gm).rotate(mouseX,mouseY);
			}
		});
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				gm.shoot();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}
}
