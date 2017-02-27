import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;


public class MovingObjectsPanel extends JPanel {
	
	final Dimension defaultDim;// = new Dimension(800,600);
	GameMap gm;
	GameObject T;
	private Timer t;
	public static int w, h;
	
	
	public MovingObjectsPanel() {
		this( new Dimension(800,600));
	}
	
	public MovingObjectsPanel(Dimension dim) {
		defaultDim = dim;
		this.setPreferredSize(defaultDim);
		makeGameMap();
		t.start();
	}
	private void makeGameMap() {
		gm = new DiepIOMap();
		T = (GameObject) gm.movers().get(0);//player tank
		//timer
		t = new Timer(10, new ActionListener() {// fires off every 10 ms
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gm.tick();// I tell the GameMap to tick... do what
					// you do every time the clock goes off.
				setUpKeyMappings();
				repaint();// naturally, we want to see the new view
			}
				
		});
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(gm.backgroundImage, 0, 0,this.getWidth(),this.getHeight() ,null);
		g.drawImage(T.img(), (int)T.x(), (int)T.y(),(int)T.size(),(int)T.size() ,null);
	}

	
	private void setUpKeyMappings() {
		// maps keys with actions...
		//  The code below maps a KeyStroke to an action to be performed
		// In this case I mapped the space bar key to the action named "shoot"
		// Whenever someone hits the Space Bar the action shoot is sent out

		this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"shoot");
		this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "move_up");
		this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "move_down");
		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "move_right");
		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "move_left");
		//diagonal movement

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
		//up
		this.getActionMap().put("move_up",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				T.move();
			}
		});
		//down
		this.getActionMap().put("move_down",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent u) {
				// TODO Auto-generated method stub
				T.move();
			}
		});
		//right
		this.getActionMap().put("move_right",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//turn right
				T.set_x(T.x() +10);
				T.checkOffScreen();
			}
		});
		//left
		this.getActionMap().put("move_left",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//turn left
				}
		});
		//Diagonal movement
		
	
		this.requestFocusInWindow();		
	}

	

}
