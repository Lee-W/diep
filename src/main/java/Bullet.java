import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bullet extends MovingObject {
    private static final String IMAGE_PATH = "images/BULLET.png";
	public int damage;
	public boolean isActive;

	private java.util.List<AITank> aiTanks;
	private PlayerTank playerTank;

	public Bullet(double speed, double direction, double size, double health, int dmg, Dimension dim, double x, double y, PlayerTank playerTank, java.util.List<AITank> aiTanks){
		super(speed, direction, size, health, dim);
		loadImage(IMAGE_PATH);

		this.damage = dmg;
		this.aiTanks = aiTanks;
		this.playerTank = playerTank;
		this.x = x;
		this.y = y;

		isActive = true;

		this.fire();
	}

	@Override
	public void initialCoordinate() {

    }

	public void fire() {
		Timer t = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (x >= 0 && x <= screenWidth && y >= 0 && y <= screenHeight) {
					move((int) Math.round(direction/90));
					if (playerTank != null) {
                        checkHit(playerTank, e);
					}

					if (aiTanks != null) {
						for (AITank t : aiTanks) {

							if (checkCollision(t.getBoundingRect())) {
								t.hit(Bullet.this);
								isActive = false;
								((Timer) e.getSource()).stop();
							}
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

	private void checkHit(MovingObject tank, ActionEvent event) {
        if (checkCollision(tank.getBoundingRect())) {
            playerTank.hit(Bullet.this);
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
		g.drawImage(image, (int) x, (int) y, (int) size, (int) size, null);
	}
}