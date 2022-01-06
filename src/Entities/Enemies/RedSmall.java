package Entities.Enemies;

import Entities.Bullets.Bullet;
import Entities.Entity;
import Entities.HpBar;

public class RedSmall extends Entity {

    public RedSmall(int x, int y, int hp, int hpBarX) {
        super(x, y, 120, 120, hp, 3,
                49, 74, 38, 63,
                "ENEMY", "red_enemy/enemy1_");
        hpBar = new HpBar(hpBarX, 25, hp, name);

    }

    @Override
    protected void shot() {
        if (animationCounter == 57) {
            bullets.add(new Bullet(x + width / 2, y + height, "bullets/bullet_red.png", true ));
        }
    }
}
