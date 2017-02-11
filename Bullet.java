import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Bullet extends GameObject {
	public int damage;
    public boolean isActive;

	private Tank tank;

	public Bullet(double speed, double direction, double size, double health, int dmg, Dimension dim, double x, double y, Tank tank){
		super(speed,direction,size,health, dim);

        this.damage = dmg;

        this.x = x;
        this.y = y;

        isActive = true;

		this.tank = tank;
        this.fire();
	}

	public void fire() {
        Timer t = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (x >= 0 && x <= screenDim.getWidth() && y >= 0 && y <= screenDim.getHeight()) {
                    move();
					if (checkCollision(tank.getBoundingRect())) {
                        System.out.println("Tank hit!");
                        tank.hit(Bullet.this);
					}
                } else {
                    isActive = false;
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        t.start();
    }

	@Override
	public void checkOffScreen() {
		ArrayList<Double> l = getPos();
		double x = l.get(0);
		double y = l.get(1);

		double screenX = 0;
		double screenY = 0;

		/*if (x>screenX || y>screenY){

		}*/
	}

	public boolean checkCollision(Rectangle r){
		Rectangle rect = getBoundingRect();
		return rect.intersects(r);
	}

	@Override
	public void draw(Graphics g) {
        g.drawImage(img, (int) x, (int) y, (int) size, (int) size, null);
	}

	@Override
	public void setImagePath() {
        imagePath = "images/BULLET.png";
	}
}