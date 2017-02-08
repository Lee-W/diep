import java.awt.Color;
import java.awt.Image;
import java.awt.List;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;


public abstract class GameObject extends JPanel implements MovingObject{

	public double speed;// 0 - 10
	public double direction, // degrees or radians
		x, y, // >= 0
		size, // 10 might be a good size   
		health; // 0 - 100
	public Image img;

	public GameObject(double speed,double direction,double size,double health,Image img){
		this.speed = speed;
		this.direction = direction;
		this.size = size;
		this.health = health;
		this.img = img;
	}
	
	@Override
	public void move() {
		x+= speed*Math.cos(Math.toRadians(direction));
		y+= speed*Math.sin(Math.toRadians(direction));
		
		checkOffScreen();
		// maybe "push" back onto the screen change direction if
		// this object goes off the screen
	}
	
	public ArrayList<Double> getPos(){
		ArrayList<Double> l = new ArrayList<Double>();
		l.add(x);
		l.add(y);
		return l;
	}
	
	public Image getImage(){
		return img;
	}

	public abstract void checkOffScreen();
	
	
	@Override
	public Rectangle getBoundingRect() {
		return new Rectangle((int)x,(int)y,(int)size,(int)size);
	}
	
	
}
