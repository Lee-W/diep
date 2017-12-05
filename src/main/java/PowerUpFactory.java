import java.awt.*;

public class PowerUpFactory {
    public static BulletDamage createBulletDamge(Dimension mapSize, PlayerTank playerTank) {
        return new BulletDamage(mapSize, playerTank);
    }

    public static BulletSpeed createBulletSpeed(Dimension mapSize, PlayerTank playerTank) {
        return new BulletSpeed(mapSize, playerTank);
    }

    public static HealthBonus createHealthBonus(Dimension mapSize, PlayerTank playerTank) {
        return new HealthBonus(mapSize, playerTank);
    }
}
