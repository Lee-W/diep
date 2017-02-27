import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MovingObjectsPanel extends JPanel {

	final Dimension defaultDim;
	GameMap gm;

	private double lastShot = System.currentTimeMillis();

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
		new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}

		}).start();

		Timer aiTank = new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((DiepIOMap) gm).aiShoot();
			}
		});

		aiTank.start();
	}

	private void setUpKeyMappings() {
		this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"shoot");

		this.getInputMap().put(KeyStroke.getKeyStroke("UP"),"moveUp");
		this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"),"moveDown");
		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"),"moveLeft");
		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),"moveRight");

		/*this.getInputMap().put(KeyStroke.getKeyStroke("W"),"moveUp");
		this.getInputMap().put(KeyStroke.getKeyStroke("S"),"moveDown");
		this.getInputMap().put(KeyStroke.getKeyStroke("A"),"moveLeft");
		this.getInputMap().put(KeyStroke.getKeyStroke("D"),"moveRight");*/

		IsKeyPressed.detectKeyPress();
		new Thread(new Runnable() {
			@Override
			public void run() {
				new Timer(60, new ActionListener() {
					@Override
					public void actionPerformed (ActionEvent actionEvent){
						if (IsKeyPressed.isWPressed() || IsKeyPressed.isUpPressed()) {
							gm.move(3);
						}
						if (IsKeyPressed.isSPressed() || IsKeyPressed.isDownPressed()) {
							gm.move(1);
						}
						if (IsKeyPressed.isAPressed() || IsKeyPressed.isLeftPressed()) {
							gm.move(2);
						}
						if (IsKeyPressed.isDPressed() || IsKeyPressed.isRightPressed()) {
							gm.move(0);
						}
					}
				}).start();
			}
		}).start();

		this.getActionMap().put("shoot",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if ((System.currentTimeMillis() - lastShot)>150){
					lastShot = System.currentTimeMillis();
					gm.shoot();
				}
			}
		});

		this.getActionMap().put("moveUp",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				gm.move(3);
			}
		});

		this.getActionMap().put("moveDown",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				gm.move(1);
			}
		});

		this.getActionMap().put("moveLeft",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				gm.move(2);
			}
		});

		this.getActionMap().put("moveRight",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
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
			public void mouseDragged(MouseEvent arg0) {}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				mouseX = arg0.getX();
				mouseY = arg0.getY();
				gm.rotate(mouseX,mouseY);
			}
		});
		this.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				gm.shoot();
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
		});
	}
}
