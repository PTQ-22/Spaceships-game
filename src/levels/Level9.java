package levels;

import Entities.Bullets.Bullet;
import Entities.Enemies.LaserEnemy;
import Entities.Enemies.MissileEnemy;
import Entities.Enemies.RedSmall;
import Entities.Enemies.SurpriseEnemy;
import Entities.Entity;
import main.KeyHandler;
import main.MouseController;

import java.awt.*;
import java.util.Objects;

public class Level9 extends Level{
    private boolean phase2 = false;

    public Level9(MouseController mC, KeyHandler kH, int [] stars) {
        super(mC, kH, 9, stars);
        backgroundImage = loadBackgroundImage("/level_background/background_l9.png");
        enemiesList.add(new SurpriseEnemy());
    }

    @Override
    protected void checkWinState() {
        if (enemiesList.size() == 0) {
            if (!phase2) {
                phase2 = true;
                enemiesList.add(new MissileEnemy(500, 60, 200, 540, player));
                enemiesList.add(new LaserEnemy());
                enemiesList.add(new RedSmall(100, 400, 200, 770));
            }
            else {
                state = "win";
                setStars();
                font = new Font("FreeSans", Font.BOLD, FONT_SIZE);
            }
        }
    }

    protected void checkEnemyBulletsHits(Entity e) {
        // check if enemy bullet hits
        for (int i = 0; i < e.bullets.size(); ++i) {
            Bullet enemyBullet = e.bullets.get(i);
            if (enemyBullet.hit(player)) {
                startDrawingHit(enemyBullet.x, enemyBullet.y, enemyBullet.hitDrawScale);
                e.bullets.remove(i);
                --i;
                player.decreaseHp(enemyBullet.getPower());
            }
        }
        if (Objects.equals(e.name, "LASERSHIP")) {
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
}
