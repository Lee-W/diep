import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bullet extends GameObject {
	public int damage;
	public boolean isActive;

	private java.util.List<AITank> aiTanks;
	private Tank tank;

	public Bullet(double speed, double direction, double size, double health, int dmg, Dimension dim, double x, double y, Tank tank, java.util.List<AITank> aiTanks){
		super(speed,direction,size,health, dim);

		this.damage = dmg;

		this.aiTanks = aiTanks;
		this.tank = tank;

		this.x = x;
		this.y = y;

		isActive = true;

		this.fire();
	}

	public void fire() {
		Timer t = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (x >= 0 && x <= screenDim.getWidth() && y >= 0 && y <= screenDim.getHeight()) {
					move((int) Math.round(direction/90));
					if (tank != null) {
                        checkHit(tank, e);
					}

					if (aiTanks != null) {
						for (AITank t : aiTanks) {
						    checkHit(t, e);
						}
					}
				} else {
					isActive = false;
					((Timer) e.getSource()).stop();
				}
			}
		});

		t.start();
	}

	private void checkHit(GameObject tank, ActionEvent event) {
        if (checkCollision(tank.getBoundingRect())) {
            tank.hit(Bullet.this);
            isActive = false;
            ((Timer) event.getSource()).stop();
        }
    }

	@Override
	public void checkOffScreen() {
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