import java.awt.*;

public class BulletSpeed extends PowerUp {
    private static final String IMG_PATH = "images/BULLETx2.png";

    public BulletSpeed(Dimension dim, Tank tank) {
        super(2, 15, dim, tank);
        Image img = openImage(IMG_PATH);
        loadImage(img);
    }
}
