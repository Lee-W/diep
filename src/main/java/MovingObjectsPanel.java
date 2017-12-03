import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MovingObjectsPanel extends JPanel {
    private final Dimension DEFAULT_DIM;

    private GameMap gameMap;

    private double lastShot = System.currentTimeMillis();

    private int mouseX = 0;
    private int mouseY = 0;

    public MovingObjectsPanel(Dimension dim) {
        DEFAULT_DIM = dim;
        this.setPreferredSize(DEFAULT_DIM);
        makeGameMap();

        setUpMotionListener();
        setUpKeyMappings();
    }

    private void makeGameMap() {
        gameMap = new DiepIOMap(this.DEFAULT_DIM);
        new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        }).start();

        new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((DiepIOMap) gameMap).aiShoot();
            }
        }).start();

        new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((DiepIOMap) gameMap).aiDodge();
            }
        }).start();
    }

    private void setUpKeyMappings() {
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"shoot");
        this.getInputMap().put(KeyStroke.getKeyStroke("E"), "autoFireToggle");

        this.getActionMap().put("autoFireToggle", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((DiepIOMap) gameMap).toggleAutoFire();
            }
        });

        IsKeyPressed.detectKeyPress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Timer(60, new ActionListener() {
                    @Override
                    public void actionPerformed (ActionEvent actionEvent){
                        if (IsKeyPressed.isWPressed() || IsKeyPressed.isUpPressed()) {
                            gameMap.move(3);
                        }
                        if (IsKeyPressed.isSPressed() || IsKeyPressed.isDownPressed()) {
                            gameMap.move(1);
                        }
                        if (IsKeyPressed.isAPressed() || IsKeyPressed.isLeftPressed()) {
                            gameMap.move(2);
                        }
                        if (IsKeyPressed.isDPressed() || IsKeyPressed.isRightPressed()) {
                            gameMap.move(0);
                        }
                    }
                }).start();
            }
        }).start();

        this.getActionMap().put("shoot",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((System.currentTimeMillis() - lastShot) > 150){
                    lastShot = System.currentTimeMillis();
                    gameMap.shoot();
                }
            }
        });

        this.getActionMap().put("moveUp",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMap.move(3);
            }
        });

        this.getActionMap().put("moveDown",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMap.move(1);
            }
        });

        this.getActionMap().put("moveLeft",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMap.move(2);
            }
        });

        this.getActionMap().put("moveRight",new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMap.move(0);
            }
        });

        this.requestFocusInWindow();
    }

    public void paintComponent(Graphics g){
        gameMap.draw(g);
    }

    private void setUpMotionListener(){
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent arg0) {}

            @Override
            public void mouseMoved(MouseEvent arg0) {
                mouseX = arg0.getX();
                mouseY = arg0.getY();
                gameMap.rotate(mouseX,mouseY);
            }
        });
        this.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent arg0) {
                gameMap.shoot();
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
