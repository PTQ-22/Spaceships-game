package Entities.Enemies;

import Entities.Bullets.Bullet;
import Entities.Bullets.Missile;
import Entities.Entity;
import Entities.HpBar;
import Entities.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Boss extends Entity {

    public boolean isShield = true;
    private final BufferedImage shieldImg;
    private final BufferedImage hpBarShieldImg;
    private final int hpBarX = 40;
    private final int hpBarY = 25;
    private int missileCounter = 0;
    private boolean missileShot = false;
    private final Player player;
    public final ArrayList<Engine> engines = new ArrayList<>();

    public Boss(Player player) {
        super(100, 40, 300, 300, 550, 2, 126, 173, 120, 160,
                "BOSS", "boss/boss_");
        hpBar = new BossHpBar(hpBarX, hpBarY, hp, name);
        scaleBoomImages(3);
        shieldImg = loadImage("/shield/shield.png");
        hpBarShieldImg = loadImage("/shield/hp_shield.png");
        this.player = player;
        engines.add(new Engine(25, 1, 605, 22));
        engines.add(new Engine(70, 2, 605, 64));
        engines.add(new Engine(200, 3, 805, 22));
        engines.add(new Engine(245, 4, 805, 64));
    }

    @Override
    public void draw(Graphics2D g2) {
        animationCounter++;
        if (animationCounter >= 58) {
            animationCounter = 0;
            missileCounter += 1;
            missileShot = true;
        }
        g2.drawImage(images[animationCounter / 6], x, y, null);
        if (isShield)  {
            g2.drawImage(shieldImg, x, y, null);
            for (Engine e: engines) {
                e.draw(g2);
                e.drawHpBar(g2);
            }
        }
    }

    @Override
    protected void shot() {
        int firePlaceX = x + 145;
        if (animationCounter == 1) {
            bullets.add(new Bullet(firePlaceX - 15, y + height, "bullets/bullet_red.png", true));
            bullets.add(new Bullet(firePlaceX, y + height, "bullets/bullet_red.png", true));
            bullets.add(new Bullet(firePlaceX + 15, y + height, "bullets/bullet_red.png", true));
        }
        if (missileShot) {
            if (missileCounter % 5 == 0) {
                bullets.add(new Missile(x, y + 180, "missile/missile_8.png", true, player.x));
                bullets.add(new Missile(x + 290, y + 180, "missile/missile_8.png", true, player.x));
            }
            missileShot = false;
        }
        if (isShield){
            for (Engine e: engines) e.move();
            if (engines.size() == 0) {
                isShield = false;
            }
        }
    }

    private class BossHpBar extends HpBar {

        public BossHpBar(int x, int y, int hp, String text) {
            super(x, y, hp, text);
        }

        @Override
        public void draw(Graphics2D g2) {
            // text
            g2.setColor(Color.black);
            g2.setFont(font);
            g2.drawString(TEXT, X, FONT_Y);
            // border
            g2.fillRect(X, Y, WIDTH, HEIGHT);
            // hp
            g2.setColor(Color.red);
            g2.fillRect(X + BORDER_SIZE, Y + BORDER_SIZE, WIDTH - 2 * BORDER_SIZE, HEIGHT - 2 * BORDER_SIZE);
            g2.setColor(Color.green);
            g2.fillRect(X + BORDER_SIZE, Y + BORDER_SIZE, hp, HEIGHT - 2 * BORDER_SIZE);
            if (isShield) {
                g2.drawImage(hpBarShieldImg, hpBarX - 2, hpBarY - 5, null);
            }
        }
    }

    public class Engine extends Entity {
        private final int fromBossX;
        public Engine(int x, int id, int hpBarX, int hpBarY) {
            super(Boss.this.x + x, 130, 30, 100, 180, 2, 0, 0, 0, 100,
                    "ENGINE " + id, "engine/engine_");
            fromBossX = x;
            hpBar = new HpBar(hpBarX, hpBarY, hp, name);
            scaleBoomImages(0.8);

        }

        @Override
        public void move() {
            x = Boss.this.x + fromBossX;
            hpBar.setHp(hp);
        }
        @Override
        public void drawBoomAnimation(Graphics2D g2) {
            boomAnimationCounter++;
            if (boomAnimationCounter >= 20) {
                isBoom = false;
            }
            g2.drawImage(boomImages[boomAnimationCounter / 2], x, y, null);
        }

        @Override
        protected void shot() {}

    }
}
