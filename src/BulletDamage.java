import java.awt.*;

public class BulletDamage extends PowerUp {

    public BulletDamage(int b, long d, Dimension dim, Tank tank) {
        super(b, d, dim, tank);
    }

    @Override
    public void setImagePath() {
        imagePath = "images/DAMAGEx2.png";
    }
}
