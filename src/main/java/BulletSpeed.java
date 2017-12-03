import java.awt.*;

public class BulletSpeed extends PowerUp {
    private static final String IMG_PATH = "images/BULLETx2.png";

    public BulletSpeed(int b, long d, Dimension dim, Tank tank) {
        super(b, d, dim, tank);
        Image img = openImage(IMG_PATH);
        loadImage(img);
    }
}
