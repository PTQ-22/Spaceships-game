package Entities;

import Entities.Bullets.Bullet;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity{
    KeyHandler keyHandler;
    BufferedImage[] imagesTurnRight = new BufferedImage[NUM_OF_IMAGES];
    BufferedImage[] imagesTurnLeft = new BufferedImage[NUM_OF_IMAGES];
    BufferedImage[] currentImages;
    private final int START_HP;

    public Player(KeyHandler kH) {
        keyHandler = kH;

        this.x = 300;
        this.y = 600;
        this.width = 120;
        this.height = 120;
        this.pointX1 = 49;
        this.pointX2 = 74;
        this.pointY1 = 38;
        this.pointY2 = 63;

        this.speed = 6;

        this.hp = 200;
        START_HP = hp;
        this.name = "PLAYER";
        this.hpBar = new HpBar(20, 720, hp, name);

        bullets = new ArrayList<>();

        loadAllImages("player/player_");
        currentImages = images;
        for (int i = 0; i < NUM_OF_IMAGES; ++i) {
            imagesTurnRight[i] = loadImage("../images/player/player_turn_right_test.png");
            imagesTurnLeft[i] = loadImage("../images/player/player_turn_left_test.png");
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        animationCounter++;
        if (animationCounter >= 58) {
            animationCounter = 0;
        }
        g2.drawImage(currentImages[animationCounter / 6], x, y, null);

        hpBar.draw(g2);
    }

    @Override
    public void move() {
        if (keyHandler.rightPressed && !keyHandler.leftPressed) {
            x += speed;
            currentImages = imagesTurnRight;
        }
        else if (keyHandler.leftPressed && !keyHandler.rightPressed) {
            x -= speed;
            currentImages = imagesTurnLeft;
        }
        else currentImages = images;

        if (keyHandler.upPressed) y -= speed;
        if (keyHandler.downPressed) y += speed;

        if (keyHandler.spaceTyped) {
            keyHandler.spaceTyped = false;
            bullets.add(new Bullet(x + width / 2, y, "bullets/bullet_green.png", false));
        }

        hpBar.setHp(hp);
        detectBorderCollision();
    }

    // TODO change this to work with all enemies
    public boolean collision(Entity enemy) {
        if ((enemy.x + enemy.width) > this.x && enemy.x < (this.x + this.width))
            if ((enemy.y + enemy.height - 45) > this.y && (enemy.y + 55) < (this.y + this.height))
                return true;
        if ((enemy.x + enemy.width - 40) > (this.x + 40) && (enemy.x + 40) < (this.x + this.width - 40))
            if ((enemy.y + enemy.height) > this.y && enemy.y < (this.y + this.height))
                return true;
        return false;
    }

    private void detectBorderCollision() {
        if (x <= 0) x = 0;
        if (x >= 1000 - width) x = 1000 - width;
        if (y <= 0) y = 0;
        if (y >= 750 - height) y = 750 - height;
    }

    @Override
    protected void loadAllImages(String imgFolderAndFilePathName) {
        for (int i = 1; i <= NUM_OF_IMAGES; ++i) {
            String IMG_PATH = "../images/" + imgFolderAndFilePathName;
            images[i - 1] = loadImage(IMG_PATH + i + ".png");
        }
    }

    public int getSTART_HP() {
        return START_HP;
    }
}
