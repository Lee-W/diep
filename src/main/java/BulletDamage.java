import java.awt.*;

public class BulletDamage extends PowerUp {
    private static final String IMG_PATH = "images/DAMAGEx2.png";

    public BulletDamage(Dimension dim, Tank tank) {
        super(2, 15, dim, tank);

        Image img = openImage(IMG_PATH);
        loadImage(img);
    }
}
