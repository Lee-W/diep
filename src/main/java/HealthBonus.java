import java.awt.*;

public class HealthBonus extends PowerUp {
    private static final String IMAGE_PATH = "images/HEALTH.png";

    public HealthBonus(Dimension dim, Tank tank) {
        super(1000, 0, dim, tank);

        Image img = openImage(IMAGE_PATH);
        loadImage(img);
    }
}