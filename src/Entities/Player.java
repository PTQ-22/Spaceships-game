package Entities;

import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    KeyHandler keyHandler;
    BufferedImage[] imagesTurnRight = new BufferedImage[NUM_OF_IMAGES];
    BufferedImage[] imagesTurnLeft = new BufferedImage[NUM_OF_IMAGES];
    BufferedImage[] currentImages;

    public Player(KeyHandler kH) {
        keyHandler = kH;

        this.x = 300;
        this.y = 600;
        this.width = 120;
        this.height = 120;

        this.speed = 6;

        this.hp = 200;
        this.name = "PLAYER";
        this.hpBar = new HpBar(20, 720, hp, name);

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
        if (keyHandler.rightPressed) {
            x += speed;
            currentImages = imagesTurnRight;
        }
        else if (keyHandler.leftPressed) {
            x -= speed;
            currentImages = imagesTurnLeft;
        }
        else currentImages = images;

        if (keyHandler.upPressed) y -= speed;
        if (keyHandler.downPressed) y += speed;

        detectBorderCollision();
    }

    private void detectBorderCollision() {
        if (x <= 0) x = 0;
        if (x >= 1000 - width) x = 1000 - width;
        if (y <= 0) y = 0;
        if (y >= 750 - height) y = 750 - height;
    }
}
