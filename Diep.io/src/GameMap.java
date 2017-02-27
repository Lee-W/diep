import java.awt.Image;
import java.util.ArrayList;
import java.util.List;


public abstract class GameMap {

	private List<MovingObject> movers;
	Image backgroundImage;
	
	public GameMap() {
		movers = new ArrayList();
		openBackgroundImage();
	}
	
	public List<MovingObject> movers(){
		return movers;
	}

	public void addGameObject(GameObject go) {
		movers.add(go);
	}
	public abstract void openBackgroundImage();

	public void shoot() {
		// TODO Auto-generated method stub
		
	}

	public void tick() {
		// TODO Auto-generated method stub
		
	} 	
}
