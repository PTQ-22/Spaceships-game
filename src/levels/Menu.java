package levels;

import button.Button;
import button.StarsButton;
import main.MouseController;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Menu extends Level{
    private final int NUM_OF_IMAGES = 11;
    private final BufferedImage[] backgroundImages = new BufferedImage[NUM_OF_IMAGES];
    private int animationCounter = 0;

    private final int NUM_OF_BUTTONS = 10;

    Button delButton = new Button(820, 12, 150, 25, new Color(255, 0, 0), new Color(200, 0, 0), "Delete Progress");
    Button[] buttons = new Button[NUM_OF_BUTTONS];

    public Menu() {
        // load background images
        for (int i = 1; i <= NUM_OF_IMAGES; ++i) {
            String IMG_PATH = "../images/menu_background/background_start_";
            backgroundImages[i - 1] = loadBackgroundImage(IMG_PATH + i + ".png");
        }
        // create buttons array
        buttons[0] = delButton;
        int yPosCounter = 60;
        for (int i = 1; i < NUM_OF_BUTTONS; ++i) {
            buttons[i] = new StarsButton(700, yPosCounter, 280, 50, new Color(0, 0, 150), new Color(0, 0, 100), "Level " + i);
            yPosCounter += 70;
        }
    }

    @Override
    public void update(MouseController mouseController) {
        for (Button b: buttons) {
            if (b.isMouse(mouseController.mousePos)) {
                b.changeColorToFocus();
                if (mouseController.clicked) {
                    System.out.println("Click");
                    mouseController.clicked = false;
                }
            }
            else {
                b.changeColorToDefault();
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        drawBackground(g2);

        for (Button b: buttons) {
            b.draw(g2);
        }
    }

    @Override
    protected void drawBackground(Graphics2D g2) {
        // Draws animated background
        animationCounter++;
        if (animationCounter >= 58) {
            animationCounter = 0;
        }
        g2.drawImage(backgroundImages[animationCounter / 6], 0, 0, null);
    }
}
