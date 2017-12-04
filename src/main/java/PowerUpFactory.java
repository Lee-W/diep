import java.awt.*;

public class PowerUpFactory {
    public static BulletDamage createBulletDamge(Dimension mapSize, Tank playerTank) {
        return new BulletDamage(mapSize, playerTank);
    }

    public static BulletSpeed createBulletSpeed(Dimension mapSize, Tank playerTank) {
        return new BulletSpeed(mapSize, playerTank);
    }

    public static HealthBonus createHealthBonus(Dimension mapSize, Tank playerTank) {
        return new HealthBonus(mapSize, playerTank);
    }
}
