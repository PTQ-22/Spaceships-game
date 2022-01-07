package Entities.Enemies;

import Entities.Entity;
import Entities.HpBar;
import Entities.Player;
import levels.Level8;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class SpawnerEnemy extends Entity {
    private final BufferedImage image;
    private final BufferedImage discImage;
    private int spawnCounter = 0;
    private int angleCounter = 0;
    Level8 level;
    Player player;

    public SpawnerEnemy(Level8 level, Player player) {
        this.player = player;
        this.level = level;
        x = 300;
        y = 50;
        width = 350;
        height = 250;
        pointX1 = 75;
        pointX2 = 275;
        pointY1 = 0;
        pointY2 = 73;
        speed = 1;
        hp = 800;
        name = "BOSS";
        hpBar = new HpBar(40, 25, hp, name);
        image = loadImage("/spawner_enemy/spawner_enemy.png");
        discImage = loadImage("/spawner_enemy/disc_right_down.png");
        loadBoomImages();
        scaleBoomImages(3);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, null);

        if (angleCounter == 360) angleCounter = 0;
        angleCounter += 5;
        drawDisc(g2);
    }

    private void drawDisc(Graphics2D g2) {
        double rotationRad = Math.toRadians(angleCounter);

        AffineTransform tx = AffineTransform.getRotateInstance(
                rotationRad, (double) discImage.getWidth() / 2, (double) discImage.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g2.drawImage(op.filter(discImage, null), x + 35, y - 10, null);
    }

    @Override
    public void move() {
        if (x <= 200) goRight = true;
        if (x >= 800 - width) goRight = false;
        if (goRight) x += speed;
        else x -= speed;

        hpBar.setHp(hp);
        shot();
    }

    @Override
    protected void shot() {
        spawnCounter += 1;
        if (spawnCounter == 200) {
            spawnCounter = 0;
            level.addEnemy(new SpawnedEnemy(x - 50, y, true));
            level.addEnemy(new SpawnedEnemy(x + width, y, false));
        }
    }

    public class SpawnedEnemy extends Entity{
        BufferedImage image, discImage;
        boolean isLeft;
        public boolean isStick = false;
        int angleCounter = 0;
        int dropCounter = 0;

        public SpawnedEnemy(int x, int y, boolean isLeft) {
            this.x = x;
            this.y = y;
            this.isLeft = isLeft;
            canCollision = false;
            width = 50;
            height = 50;
            pointX1 = -10;
            pointX2 = 60;
            pointY1 = 0;
            pointY2 = 50;
            speed = 3;
            hp = 90;
            name = "BOSS";
            image = loadImage("/spawner_enemy/spawned_1.png");
            discImage = loadImage("/spawner_enemy/small_disc.png");
            for (int i = 1; i <= NUM_OF_IMAGES; ++i) {
                this.boomImages[i - 1] = loadImage("/boom_animation/boom_" + i + ".png");
            }
        }
        @Override
        public void draw(Graphics2D g2) {
            g2.drawImage(image, x, y, null);

            if (this.angleCounter == 360) angleCounter = 0;
            angleCounter += 5;

            drawDisc(g2, x - 12);
            drawDisc(g2, x + 37);
        }

        @Override
        public void move() {
            if (dropCounter <= 20) {
                dropCounter++;
                if (goRight) {
                    if (isLeft) x -= 2;
                    else x += 3;
                }
                else {
                    if (isLeft) x += 3;
                    else x -= 2;
                }
            }
            else {
                if (!isStick) {
                    if (y >= player.y && y < player.y + 100) {
                        if (x < player.x + 74 && x + width > player.x + 49) {
                            isStick = true;
                        }
                    }
                    else {
                        y += speed;
                    }
                }
                else {
                    x = player.x + 25;
                    y = player.y + 20;
                }
                if (x > player.x + 50) x -= speed;
                else if (x < player.x - 50) x += speed;
            }

        }

        private void drawDisc(Graphics2D g2, int x) {
            double rotationRad = Math.toRadians(angleCounter);

            AffineTransform tx = AffineTransform.getRotateInstance(
                    rotationRad, (double) discImage.getWidth() / 2, (double) discImage.getHeight() / 2);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            g2.drawImage(op.filter(discImage, null), x, y + 25, null);
        }

        @Override
        protected void shot() {}
        @Override
        public void drawHpBar(Graphics2D g2) {}
    }

    private void loadBoomImages() {
        for (int i = 1; i <= NUM_OF_IMAGES; ++i) {
            boomImages[i - 1] = loadImage("/boom_animation/boom_" + i + ".png");
        }
    }
}
