import java.awt.*;

public class HealthBonus extends PowerUp {

    public HealthBonus(int b, long d, Dimension dim, Tank tank) {
        super(b, d, dim, tank);
    }

    @Override
    public void setImagePath() {
        imagePath = "images/HEALTH.png";
    }

}