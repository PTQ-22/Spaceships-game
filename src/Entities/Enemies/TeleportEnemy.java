package Entities.Enemies;

import Entities.Bullets.Bullet;
import Entities.Entity;
import Entities.HpBar;

import java.util.Random;

public class TeleportEnemy extends Entity {
    private final Random rand = new Random();

    public TeleportEnemy(int x, int y, int hp, int hpBarX) {
        super(x, y, 120, 120, hp, 3,
                0, 0, 0, 120,
                "ENEMY", "teleporter_enemy/tel_enemy_");
        hpBar = new HpBar(hpBarX, 25, hp, name);

    }

    private void teleport() {
        if (x < 350) x += 400;
        else if (x > 350) x -= 400;
    }

    @Override
    public void move() {
        if (x <= 0) goRight = true;
        if (x >= 1000 - width) goRight = false;
        if (goRight) x += speed;
        else x -= speed;

        if (animationCounter == 2) {
            if (rand.nextInt(2) == 1) {
                teleport();
            }
        }
        hpBar.setHp(hp);
        shot();
    }

    @Override
    protected void shot() {
        if (animationCounter == 57) {
            bullets.add(new Bullet(x, y + height, "bullets/bullet_red.png", true ));
            bullets.add(new Bullet(x + width / 2, y + height, "bullets/bullet_red.png", true ));
            bullets.add(new Bullet(x + width, y + height, "bullets/bullet_red.png", true ));
        }
    }
}
