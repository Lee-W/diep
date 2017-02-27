import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AITank extends GameObject {

	boolean isAlive = true;
	private Timer t;

	private int PlayerTankX = 0;
	private int PlayerTankY = 0;

	private double LastShotDir = 0;

	private int XGoal = 0;
	private int YGoal = 0;
	private double GoalDir = 0;

	private double moveDir = 0;

	private boolean Stop = false;
	private int StopCounter = 0;

	private boolean Dodging = false;
	private int DodgeCounter = 0;
	
	private int moveCounter = 0;

	public AITank(double speed, double size, double health, Dimension dim) {
		super(speed, 0, size, health, dim);
		double direction = Math.random() * 4;

		x = Math.random() * dim.getWidth();
		y = Math.random() * dim.getHeight();

		setDirection((int) direction);
		setTimers();
	}

	@Override
	public void draw(Graphics g) {
		g.drawRect((int) x, (int) y + (int) size, (int) size, 10);

		g.setColor(new Color(255, 0, 0));
		if (health < 75) {
			g.setColor(new Color(255, 150, 0));
		}
		if (health < 30) {
			g.setColor(new Color(0, 250, 0));
		}

		g.fillRect((int) x, (int) y + (int) size, (int) ((health / 100.0) * size), 10);

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.rotate(Math.toRadians(direction) + (Math.PI / 2), x + size / 2, y + size / 2);
		g2d.drawImage(img, (int) x, (int) y, (int) size, (int) size, null);
	}

	@Override
	public void setImagePath() {
		imagePath = "images/AITANK.png";
	}

	public void UpdateXY(int x2, int y2) {
		PlayerTankX = x2;
		PlayerTankY = y2;
	}

	public void setTimers() {
		t = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Stop == true) {
					if (StopCounter < 60) {
						StopCounter++;
					} else {
						StopCounter = 0;
						moveCounter = 0;
						Stop = false;
					}
				}
				if (Dodging == true){
					if (DodgeCounter < 20){
						DodgeCounter++;
					}else{
						moveDir = 0;
						Dodging = false;
					}
				}
				autoMove();
				// DodgeBulletMove();
			}
		});
		t.start();
	}

	@Override
	public void hit(Bullet obj) {
		health -= obj.damage;
		if (health <= 0 && this.getClass().equals(AITank.class)) {
			t.stop();
			isAlive = false;
		}
	}

	public double getSize() {
		return size;
	}

	public void autoMove() {
		int length = (PlayerTankY - (int) y);
		int width = (PlayerTankX - (int) x);
		double cot = Math.atan((double) length / (double) width);
		if (PlayerTankX - x < 0) {
			cot = cot + Math.PI;
		}

		if ((Stop == false) && (Dodging == false)) {
			
			moveCounter++;
			if ((XGoal == 0) || (moveCounter > 50) || ((Math.abs(XGoal - x) < 10) && (Math.abs(YGoal - y) < 10) )) {

				int range = 30;

				XGoal = Math.max(0, PlayerTankX + (int) (Math.random() * range) - range);
				YGoal = Math.max(0, PlayerTankY + (int) (Math.random() * range) - range);

				int l2 = (YGoal - (int) y);
				int w2 = (XGoal - (int) x);
				double cot2 = Math.atan((double) l2 / (double) w2);
				GoalDir = cot2;
				if (XGoal - x < 0) {
					GoalDir = cot2 + Math.PI;
				}
				Stop = true;
			}
			setDirection(Math.toDegrees(GoalDir));
			if (Dodging){
				move2(Math.toDegrees(moveDir));
			}else{
				move2(Math.toDegrees(GoalDir));
			}
		} else {
			if (Dodging){
				move2(Math.toDegrees(moveDir));
			}else{
				setDirection(Math.toDegrees(cot));
			}
			//setDirection(Math.toDegrees(cot));
		}

	}

	public boolean DodgeBulletMove() {
		if (moveDir != 0) {
			if (Dodging == false){
				Dodging = true;
				DodgeCounter = 0;
			}
			//move2(Math.toDegrees(moveDir));
		}
		if (LastShotDir != 0) {
			double slope = Math.tan(LastShotDir);
			double xPoint = PlayerTankX;
			double yPoint = PlayerTankY;
			boolean hit = false;
			while ((yPoint >= 0 && yPoint <= screenDim.getHeight())
					&& ((xPoint >= 0 && xPoint <= screenDim.getWidth()))) {
				xPoint = xPoint + size;
				yPoint = yPoint + slope * size;
				if ((Math.abs(xPoint - getX()) <= size) && (Math.abs(yPoint - getY()) <= size)) {
					hit = true;
					moveDir = Math.atan(-1 / (slope));
					LastShotDir = 0;
					return true;
				}
			}
			if (hit == false) {
				xPoint = PlayerTankX;
				yPoint = PlayerTankY;
				while ((yPoint >= 0 && yPoint <= screenDim.getHeight())
						&& ((xPoint >= 0 && xPoint <= screenDim.getWidth()))) {
					xPoint = xPoint - size;
					yPoint = yPoint + slope * size;
					if ((Math.abs(xPoint - getX()) <= size) && (Math.abs(yPoint - getY()) <= size)) {
						moveDir = Math.atan(-1 / (slope));
						LastShotDir = 0;
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean getStop() {
		return Stop;

	}

	public void updateLastShot(double dir) {
		LastShotDir = dir;
	}

}