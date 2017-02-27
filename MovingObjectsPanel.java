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

        /*
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true),"moveUp");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true),"moveDown");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true),"moveLeft");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true),"moveRight");
        */

        IsKeyPressed.detectKeyPress();
        new Timer(5a0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (IsKeyPressed.isWPressed()) {
                    gm.move(3);
                }

                if (IsKeyPressed.isSPressed()) {
                    gm.move(1);
                }

                if (IsKeyPressed.isAPressed()) {
                    gm.move(2);
                }

                if (IsKeyPressed.isDPressed()) {
                    gm.move(0);
                }
            }
        }).start();

        //  This associates the command shoot with some action.  In this
        // case, the action triggers a shoot command invoked on my GameMap.  In general, whatever
        // goes in the actionPerformed method will be executed when a shoot command
        // is sent...

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
