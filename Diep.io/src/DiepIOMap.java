
public class DiepIOMap extends GameMap {

	
	public DiepIOMap() {
		addTank();
		
		
	}
	
	
	private void addTank() {
		this.addGameObject(new Tank());
		
	}


	@Override
	public void openBackgroundImage() {
		// TODO Auto-generated method stub

	}

}
