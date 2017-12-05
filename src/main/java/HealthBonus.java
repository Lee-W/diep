import java.awt.*;

public class HealthBonus extends PowerUp {
    private static final String IMAGE_PATH = "images/HEALTH.png";

    public HealthBonus(Dimension dim, PlayerTank playerTank) {
        super(1000, 0, dim, playerTank);
        loadImage(IMAGE_PATH);
    }
}