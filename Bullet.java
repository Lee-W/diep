import java.awt.*;

import java.util.ArrayList;

public class Bullet extends GameObject {
	private int damage = 10;

	public Bullet(double speed, double direction, double size, double health, int dmg, Dimension dim){
		super(speed,direction,size,health, dim);
		this.damage = dmg;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void setImagePath() {
		// TODO Auto-generated method stub

	}
}