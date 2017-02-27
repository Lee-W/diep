import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.ImageIcon;


public abstract class GameObject implements MovingObject {

	private double speed;// 0 - 10
	private double direction, // degrees or radians
		x , y , // >= 0
		size, // 10 might be a good size   
		health, // 0 - 100
		power; 
	private int	limitw;// not sure about this...
	private int level;//
	private Color color;
	private Image img;
	protected Dimension screen;
	
	public GameObject(double speed, double direction, double size, double health, int level){
		this.speed = speed;
		this.direction = direction;
		this.size = size;
		this.health =health;
		this.level = level;
	}
	
	//-----------------------------------
	//Image img.
	public Image img(){
		return img;
	}
	public void setimg(Image i){
		this.img = i;
	}
	//double size
	public double size(){
		return size;
	}
	//double x, y
	public double x(){
		return x;
	}
	public void set_x(double x){
		this.x =x;
	}
	public double y(){
		return y;
	}
	public void set_y(double y){
		this.y = y;
	}
	//double speed
	public double speed(){
		return speed;
	}
	public void set_limitw(int d){
		limitw = d;
	}
	public int limitw(){
		return limitw;
	}
	//-----------------------------------
	
	
	@Override
	public void move() {
		x+= speed*Math.cos(Math.toRadians(direction));
		y += speed*Math.sin(Math.toRadians(direction));
		
		checkOffScreen();
		// maybe "push" back onto the screen change direction if
		// this object goes off the screen
	}

	public abstract void checkOffScreen();
	
	
	
	
	
	@Override
	public Rectangle getBoundingRect() {
		
		return new Rectangle((int)x,(int)y,(int)size,(int)size);
	}

}
