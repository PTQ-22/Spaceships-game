package levels;

import Button.Button;
import Entities.Player;
import main.KeyHandler;
import main.MouseController;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Level {
    protected BufferedImage backgroundImage;
    protected Player player;
    protected Button menuButton = null;
    MouseController mouseController;
    KeyHandler keyHandler;

    public Level(MouseController mC, KeyHandler kH) {
        mouseController = mC;
        keyHandler = kH;
        player = new Player(kH);
        menuButton = new Button(5, 5, 25, 20, new Color(255, 0, 0), new Color(200, 0, 0), "X", 15);
        menuButton.fontPosXChange = -6;
    }

    protected Level() {} // for Menu different constructor

    public abstract void draw(Graphics2D g2);

    public abstract void update();

    public Level updateLevelType() {
        if (menuButton.isMouse(mouseController.mousePos)) {
            menuButton.changeColorToFocus();
            if (mouseController.clicked) {
                mouseController.clicked = false;

                return new Menu(mouseController, keyHandler);
            }
        }
        else {
            menuButton.changeColorToDefault();
        }
        mouseController.clicked = false;

        return this;
    }

    protected void drawBackground(Graphics2D g2) {
        g2.drawImage(backgroundImage, 0, 0, null);
        if (menuButton != null) {
            menuButton.draw(g2);
        }
    }

    protected BufferedImage loadBackgroundImage(String path) {
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
