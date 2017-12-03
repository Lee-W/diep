import java.awt.*;

public class BulletDamage extends PowerUp {
    private static final String IMG_PATH = "images/DAMAGEx2.png";

    public BulletDamage(int b, long d, Dimension dim, Tank tank) {
        super(b, d, dim, tank);

        Image img = openImage(IMG_PATH);
        loadImage(img);
    }
}
