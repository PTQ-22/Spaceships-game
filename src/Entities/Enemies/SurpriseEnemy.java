package Entities.Enemies;

import Entities.Bullets.Bullet;
import Entities.Entity;
import Entities.HpBar;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SurpriseEnemy extends Entity {
    BufferedImage image;

    public SurpriseEnemy() {
        x = 200;
        y = 52;
        width = 400;
        height = 400;
        hp = 800;
        speed = 1;
        pointX1 = 125;
        pointX2 = 275;
        pointY1 = 100;
        pointY2 = 310;
        name = "ENEMY";
        hpBar = new HpBar(40, 25, hp, name);
        image = loadImage("/surprise_enemy/surprise_enemy.png");
        loadBoomImages();
        scaleBoomImages(4);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y , null);
    }

    @Override
    protected void shot() {
        animationCounter++;
        if (animationCounter == 20) {
            animationCounter = 0;
            bullets.add(new Bullet(x + width / 2, y + height, "bullets/bullet_red.png", true ));
        }
    }
}
