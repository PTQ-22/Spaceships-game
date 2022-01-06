package levels;

import Button.Button;
import Button.StarsButton;
import main.KeyHandler;
import main.MouseController;
import main.StarFileController;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Menu extends Level{
    private final int NUM_OF_IMAGES = 11;
    private final BufferedImage[] backgroundImages = new BufferedImage[NUM_OF_IMAGES];
    private int animationCounter = 0;
    private final int[][] isStar;

    private final int NUM_OF_LEVELS = 9;

    Button delButton = new Button(820, 12, 150, 25, new Color(255, 0, 0), new Color(200, 0, 0), "Delete Progress", 15);
    StarsButton[] starsButtons = new StarsButton[NUM_OF_LEVELS];

    public Menu(MouseController mouseController, KeyHandler keyHandler) {
        this.mouseController = mouseController;
        this.keyHandler = keyHandler;
        // load background images
        for (int i = 1; i <= NUM_OF_IMAGES; ++i) {
            String IMG_PATH = "/menu_background/background_start_";
            backgroundImages[i - 1] = loadBackgroundImage(IMG_PATH + i + ".png");
        }
        // load star data
        int numOfStarGroups = 9;
        isStar = new int[numOfStarGroups][3];
        StarFileController starFileController = new StarFileController();
        starFileController.getAllStars(isStar);

        // create star buttons array
        int yPosCounter = 60;
        for (int i = 1; i <= NUM_OF_LEVELS; ++i) {
            starsButtons[i - 1] = new StarsButton(700, yPosCounter, 280, 50, new Color(0, 0, 150), new Color(0, 0, 100), "Level " + i, isStar[i - 1], i);
            yPosCounter += 70;
        }
    }

    @Override
    public Level updateLevelType() {
        // animate and wait for click on del button
        if (delButton.isMouse(mouseController.mousePos)) {
            delButton.changeColorToFocus();
            if (mouseController.clicked) {
                mouseController.clicked = false;
                StarFileController starFileController = new StarFileController();
                starFileController.resetAllStars();

                return new Menu(mouseController, keyHandler);
            }
        }
        else {
            delButton.changeColorToDefault();
        }

        // animate and wait for click on level buttons
        for (StarsButton b: starsButtons) {
            if (b.isMouse(mouseController.mousePos)) {
                b.changeColorToFocus();
                if (mouseController.clicked) {
                    mouseController.clicked = false;

                    int id = b.getID();
                    switch (id) {
                        case 1: return new Level1(mouseController, keyHandler, isStar[0]);
                        case 2: return new Level2(mouseController, keyHandler, isStar[1]);
                        case 3: return new Level3(mouseController, keyHandler, isStar[2]);
                        case 4: return new Level4(mouseController, keyHandler, isStar[3]);
                        case 5: return new Level5(mouseController, keyHandler, isStar[4]);
                        case 6: return new Level6(mouseController, keyHandler, isStar[5]);
                        case 7: return new Level7(mouseController, keyHandler, isStar[6]);
                        case 8: return new Level8(mouseController, keyHandler, isStar[7]);
                        case 9: return new Level9(mouseController, keyHandler, isStar[8]);
                    }
                }
            }
            else {
                b.changeColorToDefault();
            }
        }
        // reset .clicked to avoid "click on something else" problem
        mouseController.clicked = false;
        return this;
    }

    @Override
    public void draw(Graphics2D g2) {
        drawBackground(g2);

        delButton.draw(g2);
        for (StarsButton b: starsButtons) {
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

    @Override
    public void update() {}
}
