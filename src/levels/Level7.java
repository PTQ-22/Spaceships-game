package levels;

import Entities.Bullets.Bullet;
import Entities.Enemies.Boss;
import main.KeyHandler;
import main.MouseController;

public class Level7 extends Level{

    public Level7(MouseController mC, KeyHandler kH, int [] stars) {
        super(mC, kH, 7, stars);
        backgroundImage = loadBackgroundImage("/level_background/background_l7.png");
        enemiesList.add(new Boss(player));
    }

    @Override
    protected void checkPlayerBulletsHits() {
        // check if player bullet hits
        for (int i = 0; i < player.bullets.size(); ++i) {
            Bullet playerBullet = player.bullets.get(i);
            for (int j = 0; j < enemiesList.size(); ++j) {
                Boss e = (Boss)enemiesList.get(j);
                if (e.isShield && !playerBullet.hit(e)) {
                    for (int k = 0; k < e.engines.size(); ++k) {
                        Boss.Engine engine = e.engines.get(k);
                        if(playerBullet.hit(engine)) {
                            startDrawingHit(playerBullet.x, playerBullet.y, playerBullet.hitDrawScale);
                            player.bullets.remove(i);
                            --i;
                            engine.decreaseHp(playerBullet.getPower());
                            if (engine.getHp() <= 0) {
                                startBoomAnimation(engine);
                                e.engines.remove(k);
                                --k;
                            }
                        }

                    }
                }
                else if (playerBullet.hit(e) && e.isShield) {
                    startDrawingHit(playerBullet.x, playerBullet.y, playerBullet.hitDrawScale);
                    player.bullets.remove(i);
                    --i;
                }
                else if (playerBullet.hit(e)){
                    startDrawingHit(playerBullet.x, playerBullet.y, playerBullet.hitDrawScale);
                    player.bullets.remove(i);
                    --i;
                    e.decreaseHp(playerBullet.getPower());
                    if (e.getHp() <= 0) {
                        startBoomAnimation(e);
                        enemiesList.remove(j);
                        --j;
                    }
                }
            }
        }
    }
}
