import java.awt.Graphics;
import java.awt.Rectangle;

public interface MovingObject {
    void move(int dir);
    void draw(Graphics g);
    Rectangle getBoundingRect();
}
