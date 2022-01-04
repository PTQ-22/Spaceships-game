package Entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Entity {

    protected int x, y;
    protected int width, height;
    protected String name;

    protected int speed;

    HpBar hpBar;
    protected int hp;

    protected final int NUM_OF_IMAGES = 11;
    BufferedImage[] images = new BufferedImage[NUM_OF_IMAGES];
    protected int animationCounter = 0;

    protected Entity() {} // for player

    public Entity(int x, int y, int width, int height, int hp, int speed, String name, String imgFolderAndFilePathName) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.name = name;

        loadAllImages(imgFolderAndFilePathName);
    }

    public abstract void draw(Graphics2D g2);

    public abstract void move();

    protected void loadAllImages(String imgFolderAndFilePathName) {
        for (int i = 1; i <= NUM_OF_IMAGES; ++i) {
            String IMG_PATH = "../images/" + imgFolderAndFilePathName;
            images[i - 1] = loadImage(IMG_PATH + i + ".png");
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

}
