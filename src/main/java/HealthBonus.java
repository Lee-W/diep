import java.awt.*;

public class HealthBonus extends PowerUp {
    private static final String IMAGE_PATH = "images/HEALTH.png";

    public HealthBonus(int b, long d, Dimension dim, Tank tank) {
        super(b, d, dim, tank);

        Image img = openImage(IMAGE_PATH);
        loadImage(img);
    }
}