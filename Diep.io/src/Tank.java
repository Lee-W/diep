import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;





public class Tank extends GameObject {

	public Tank(double speed, double direction, double size, double health, int level) {
		super(speed, direction, size, health, level);
		this.opneImage();
		super.set_x(10);
		super.set_y(10);
//		checkOffScreen();
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void checkOffScreen() {
		super.screen =Toolkit.getDefaultToolkit().getScreenSize();
		// TODO Auto-generated method stub
		if(super.y()< 0){
			super.set_y(y()+speed());
		}
		if(super.x() < 0){
			super.set_x(x()+speed());
		}
		if(super.x() > screen.getWidth()){
			super.set_x(x()-(speed()+10));
//			System.out.println(screen.getWidth());
		}
		if(super.y()> screen.getHeight()){
			super.set_y(y()-(speed()+10));
//			System.out.println(screen.getHeight());
		}
	}

	public void opneImage() {
		// TODO Auto-generated method stub
		try {
			String imagename = "TANK";
			File im = new File("src/image/"+ imagename + ".png");
			Image i = ImageIO.read(im);
			super.setimg(i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Cannot find player tank image...");
			e.printStackTrace();
		}
	}

}
