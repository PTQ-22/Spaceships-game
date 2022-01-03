package levels;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Menu extends Level{
    private final int NUM_OF_IMAGES = 11;
    private final BufferedImage[] backgroundImages = new BufferedImage[NUM_OF_IMAGES];
    private int animationCounter = 0;

    public Menu() {
        for (int i = 1; i <= NUM_OF_IMAGES; ++i) {
            String IMG_PATH = "../images/menu_background/background_start_";
            backgroundImages[i - 1] = loadBackgroundImage(IMG_PATH + i + ".png");
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        drawBackground(g2);
    }

    @Override
    protected void drawBackground(Graphics2D g2) {
        animationCounter++;
        if (animationCounter >= 58) {
            animationCounter = 0;
        }
        g2.drawImage(backgroundImages[animationCounter / 6], 0, 0, null);
    }
}
