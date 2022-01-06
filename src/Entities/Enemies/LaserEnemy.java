package Entities.Enemies;


import Entities.Entity;
import Entities.HpBar;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LaserEnemy extends Entity {

    private boolean laser = false, flag = false;
    BufferedImage laserImg;
    public int firePlaceX = x + 67;
    public int firePlaceY = y + height - 50;

    public LaserEnemy() {
        super(300, 200, 170, 170, 400, 3,
                59, 112, 55, 107,
                "LASERSHIP", "lasership/lasership_");
        hpBar = new HpBar(40, 25, hp, name);
        laserImg = loadImage("../../images/lasership/laser_1.png");
        scaleBoomImages(1.5);
    }

    public boolean isLaser() {
        return laser;
    }

    @Override
    public void draw(Graphics2D g2) {
        firePlaceX = x + 67;
        firePlaceY = y + height - 50;
        if (laser) g2.drawImage(laserImg, firePlaceX, firePlaceY, null);
        animationCounter++;
        if (animationCounter >= 58) {
            animationCounter = 0;
        }
        g2.drawImage(images[animationCounter / 6], x, y, null);

    }

    @Override
    protected void shot() {
        // if x value ends with 00 change flag
        if (x % 100 == 0) {
            if (flag) {
                flag = false;
                laser = true;
            }
            else {
                flag = true;
                laser = false;
            }
        }
        else {
            laser = flag;
        }
    }
}
