import java.awt.*;

public class BulletSpeed extends PowerUp {

    public BulletSpeed(int b, long d, Dimension dim, Tank tank) {
        super(b, d, dim, tank);
    }

    @Override
    public void setImagePath() {
        imagePath = "images/BULLETx2.png";
    }
}
