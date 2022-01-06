package Entities.Bullets;

import Entities.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Bullet {
    public int x, y;
    protected boolean goDown;
    protected int speed = 7;
    protected int power = 30;
    public double hitDrawScale = 0.2;
    BufferedImage image;

    public Bullet(int x, int y, String imgPathName, boolean goDown) {
        this.x = x;
        this.y = y - 10;
        this.goDown = goDown;
        image = loadImage(imgPathName);
    }

    public int getPower() {
        return power;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, null);
    }

    public void move() {
        if (goDown) y += speed;
        else y -= speed;
    }

    public boolean outOfGame() {
        if (goDown) return y >= 750;
        else return y <= 0;
    }

    public boolean hit(Entity target) {
        if (this.x > (target.x + target.pointX1) && (this.x + 5) < (target.x + target.pointX2))
            if (this.y > target.y && (this.y + 5) < (target.y + target.height))
                return true;
        if (this.x > target.x && (this.x + 5) < (target.x + target.width))
            return this.y > (target.y + target.pointY1) && (this.y + 5) < (target.y + target.pointY2);
        return false;
    }

    protected BufferedImage loadImage(String path) {
        BufferedImage bg = null;
        try {
            bg = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream("../../images/" + path))
            );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return bg;
    }
}
