import java.awt.*;

public abstract class Tank extends MovingObject {
    public Tank(double speed, double direction, double size, double health, Dimension dim) {
        super(speed, direction, size, health, dim);
    }

    public abstract void hit(Bullet bullet);
}
