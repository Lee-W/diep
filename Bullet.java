import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Bullet extends GameObject {
	private int damage = 10;
	
	public Bullet(double speed,double direction,double size,double health,Image img,int dmg){
		super(speed,direction,size,health,img);
		this.damage = dmg;
	}
	
	@Override
	public void checkOffScreen() {
		ArrayList<Double> l = getPos();
		double x = l.get(0);
		double y = l.get(1);
		
		double screenX = 0;
		double screenY = 0;
		
		if(x>screenX || y>screenY){
			
		}
	}
	
	public boolean checkCollision(Rectangle r){
		Rectangle rect = getBoundingRect();
		return rect.intersects(r);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ArrayList<Double> l = getPos();
		int x = (int) l.get(0).doubleValue();
		int y = (int) l.get(1).doubleValue();
		
		Image img = getImage();
		g.drawImage(img, x, y, (int) size,(int) size,null);
	}
}
