import java.awt.*;

public class BulletSpeed extends PowerUp {
    private static final String IMAGE_PATH = "images/BULLETx2.png";

    public BulletSpeed(Dimension dim, PlayerTank playerTank) {
        super(2, 15, dim, playerTank);
        loadImage(IMAGE_PATH);
    }
}
