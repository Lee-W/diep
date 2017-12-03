import javax.imageio.ImageIO;
import java.awt.*;
import java.net.URL;

public abstract class DiepObject {
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
}
