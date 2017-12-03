import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public abstract class GameMap {
	private List<MovingObject> movingObjects = new ArrayList<>();
	protected Image background;
	protected String backgroundImagePath;

	public GameMap(String backgroundImagePath) {
		this.backgroundImagePath = backgroundImagePath;
		loadBackgroundImage();
	}

    public void loadBackgroundImage(){
        try {
            URL imgURL = getClass().getResource(this.backgroundImagePath);
            if (imgURL != null) {
                this.background = ImageIO.read(imgURL);
            }
        } catch (IOException e) {
            System.err.println("Could not open image ( " + backgroundImagePath + " )");
            e.printStackTrace();
        }
    }

	public void addGameObject(GameObject gameObject) {
		movingObjects.add(gameObject);
	}

    protected List<MovingObject> getMovingObjects() {
        return movingObjects;
    }

	public MovingObject getFirstObject() {
		return movingObjects.get(0);
	}

	public void draw(Graphics g) {
		drawBackground(g);
		drawObjects(g);
		drawScore(g);
	}

    public abstract void drawBackground(Graphics g);

	private void drawObjects(Graphics g) {
		for (MovingObject mo: movingObjects) {
			mo.draw(g);
		}
	}

	public abstract void drawScore(Graphics g);

	public abstract void move(int dir);

	public abstract void rotate(int mouseX, int mouseY);

	public abstract void shoot();

}