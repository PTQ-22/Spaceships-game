package Entities.Enemies;

import Entities.Bullets.Missile;
import Entities.Entity;
import Entities.HpBar;
import Entities.Player;

public class MissileEnemy extends Entity {

    private final Player player;

    public MissileEnemy(int x, int y, int hp, int hpBarX, Player player) {
        super(x, y, 120, 120, hp, 3,
                49, 74, 38, 63,
                "ENEMY", "missile_enemy/missile_player_");
        hpBar = new HpBar(hpBarX, 25, hp, name);
        this.player = player;
    }

    @Override
    protected void shot() {
        if (animationCounter == 57) {
            bullets.add(new Missile(
                    x + width / 2, y + height,
                    "missile/missile_8.png",
                    true, player.x
            ));
        }
    }
}
