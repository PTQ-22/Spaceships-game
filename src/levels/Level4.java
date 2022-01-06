package levels;

import Entities.Enemies.LaserEnemy;
import Entities.Entity;
import main.KeyHandler;
import main.MouseController;


public class Level4 extends Level{

    public Level4(MouseController mC, KeyHandler kH, int [] stars) {
        super(mC, kH, 4, stars);
        backgroundImage = loadBackgroundImage("/level_background/background_l1.png");
        enemiesList.add(new LaserEnemy());
    }

    @Override
    protected void checkEnemyBulletsHits(Entity e) {
        // in this case check if laser hits player
        LaserEnemy le = (LaserEnemy) e;
        if (le.isLaser()) {
            if (le.firePlaceX > player.x && le.firePlaceX + 36 < player.x + player.width) {
                if (le.firePlaceY < player.y) {
                    player.decreaseHp(3);
                }
            }
        }
    }
}
