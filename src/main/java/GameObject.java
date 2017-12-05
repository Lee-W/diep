import javax.imageio.ImageIO;
import java.awt.*;
import java.net.URL;

public abstract class GameObject {
    protected Image image;
    protected double x, y;
    protected double screenHeight, screenWidth;

    public GameObject(Dimension screenDimention) {
        this.screenHeight = screenDimention.getHeight();
        this.screenWidth = screenDimention.getWidth();
    }

    public abstract void initialCoordinate();

    public void loadImage(String imagePath) {
        Image image = openImage(imagePath);
        setImage(image);
    }

    public Image openImage(String imagePath){
        try {
            URL cardImgURL = getClass().getResource(imagePath);
            if (cardImgURL != null) {
                return ImageIO.read(cardImgURL);
            }
        } catch (Exception e) {
            System.err.println("Could not open image ( " + imagePath+ " )");
            e.printStackTrace();
        }
        return null;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setX(double x) {
       this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getX(){
        return (int) Math.round(x);
    }

    public int getY(){
        return (int) Math.round(y);
    }

}
