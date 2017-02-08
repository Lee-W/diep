import java.awt.Image;

public class Tank extends GameObject {

	public Tank(double speed, double direction, double size, double health, Image img) {
		super(speed, direction, size, health, img);
	}

	@Override
	public void checkOffScreen() {
		// TODO Auto-generated method stub
		
	}

}
