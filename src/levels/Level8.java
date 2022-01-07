package levels;

import Entities.Enemies.SpawnerEnemy;
import Entities.Entity;
import main.KeyHandler;
import main.MouseController;

public class Level8 extends Level{

    public Level8(MouseController mC, KeyHandler kH, int [] stars) {
        super(mC, kH, 8, stars);
        backgroundImage = loadBackgroundImage("/level_background/background_l1.png");
        enemiesList.add(new SpawnerEnemy(this, player));
    }

    protected void checkEnemyBulletsHits(Entity e) {
        // check if enemy bullet hits
        if (!e.canCollision) {
            SpawnerEnemy.SpawnedEnemy spEn = (SpawnerEnemy.SpawnedEnemy) e;
            if (spEn.isStick) player.decreaseHp(3);
         }
    }
}
