package Entities;

import Entities.Bullets.Bullet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Entity {

    public int x, y;
    public int pointX1, pointX2;
    public int pointY1, pointY2;
    public int width, height;
    public String name;

    protected int speed;
    protected boolean goRight = true;

    protected HpBar hpBar;
    protected int hp;

    protected final int NUM_OF_IMAGES = 11;
    protected BufferedImage[] images = new BufferedImage[NUM_OF_IMAGES];
    protected BufferedImage[] boomImages = new BufferedImage[NUM_OF_IMAGES];
    protected int animationCounter = 0;

    public ArrayList<Bullet> bullets = new ArrayList<>();
    protected boolean lostBullet = false;
    public boolean isBoom = false;
    public int boomAnimationCounter = 0;
    public boolean canCollision = true;
    protected Entity() {} // for player

    public Entity(int x, int y, int width, int height, int hp, int speed,
                  int pointX1, int pointX2, int pointY1, int pointY2,
                  String name, String imgFolderAndFilePathName) {
        this.x = x;
        this.y = y;
        this.pointX1 = pointX1;
        this.pointX2 = pointX2;
        this.pointY1 = pointY1;
        this.pointY2 = pointY2;
        this.hp = hp;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.name = name;

        loadAllImages(imgFolderAndFilePathName);
    }

    public void draw(Graphics2D g2) {
        animationCounter++;
        if (animationCounter >= 58) {
            animationCounter = 0;
        }
        g2.drawImage(images[animationCounter / 6], x, y, null);
    }

    protected abstract void shot();

    public void drawHpBar(Graphics2D g2) {
        hpBar.draw(g2);
    }

    public void move() {
        if (x <= 0) goRight = true;
        if (x >= 1000 - width) goRight = false;
        if (goRight) x += speed;
        else x -= speed;

        hpBar.setHp(hp);
        shot();
    }

    public void decreaseHp(int val) {
        hp -= val;
    }

    public int getHp() {
        return hp;
    }

    public boolean isLostBullet() {
        return lostBullet;
    }

    public void drawBullets(Graphics2D g2) {
        for (int i = 0; i < bullets.size(); ++i) {
            Bullet b = bullets.get(i);
            b.draw(g2);
        }
    }

    public void drawBoomAnimation(Graphics2D g2) {
        boomAnimationCounter++;
        if (boomAnimationCounter >= 58) {
            isBoom = false;
        }
        g2.drawImage(boomImages[boomAnimationCounter / 6], x, y, null);
    }

    public void moveBullets() {
        for (int i = 0; i < bullets.size(); ++i) {
            Bullet bullet = bullets.get(i);
            bullet.move();
            if (bullet.outOfGame()) {
                lostBullet = true;
                bullets.remove(i);
                --i;
            }
        }
    }

    protected void loadAllImages(String imgFolderAndFilePathName) {
        for (int i = 1; i <= NUM_OF_IMAGES; ++i) {
            String IMG_PATH = "/" + imgFolderAndFilePathName;
            images[i - 1] = loadImage(IMG_PATH + i + ".png");
            boomImages[i - 1] = loadImage("/boom_animation/boom_" + i + ".png");
        }
    }

    protected BufferedImage loadImage(String path) {
        BufferedImage bg = null;
        try {
            bg = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream(path))
            );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return bg;
    }

    protected void scaleBoomImages(double scale) {
        for (int i = 0; i < boomImages.length; ++i) {
            int w = boomImages[i].getWidth();
            int h = boomImages[i].getHeight();
            int integerScale = (int)Math.ceil(scale);
            BufferedImage after = new BufferedImage(w * integerScale, h * integerScale, BufferedImage.TYPE_INT_ARGB);
            AffineTransform at = new AffineTransform();
            at.scale(scale, scale);
            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            after = scaleOp.filter(boomImages[i], after);
            boomImages[i] = after;
        }
    }

    protected void loadBoomImages() {
        for (int i = 1; i <= NUM_OF_IMAGES; ++i) {
            boomImages[i - 1] = loadImage("/boom_animation/boom_" + i + ".png");
        }
    }
}
