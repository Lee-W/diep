import java.awt.*;

public class BulletDamage extends PowerUp {
    private static final String IMAGE_PATH = "images/DAMAGEx2.png";

    public BulletDamage(Dimension dim, PlayerTank playerTank) {
        super(2, 15, dim, playerTank);
        loadImage(IMAGE_PATH);
    }
}
