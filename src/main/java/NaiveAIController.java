import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NaiveAIController implements AIController {
    private List<AITank> aiTanks;
    private Dimension mapSize;
    private PlayerTank playerTank;

    public NaiveAIController(List<AITank> aiTanks, Dimension mapSize, PlayerTank playerTank) {
        this.aiTanks = aiTanks;
        this.mapSize = mapSize;
        this.playerTank = playerTank;
    }

    @Override
    public List<Bullet> shoot() {
        List<Bullet> bullets = new ArrayList<>();
        for (AITank tank : aiTanks) {
            double randomSeed = Math.random() * 1000;
            if (randomSeed <= 100) {
                Bullet aiBullet = new Bullet(
                        10,
                        tank.direction,
                        15,
                        100,
                        5,
                        mapSize,
                        tank.getX(),
                        tank.getY(),
                        playerTank,
                        null
                );
                bullets.add(aiBullet);
            }
        }
        return bullets;
    }

    @Override
    public void dodge() {
        for (AITank tank : aiTanks) {
            double randomSeed = Math.random() * 1000;
            if (randomSeed <= 250) {
                tank.dodgeBullet();
            }
        }
    }
}
