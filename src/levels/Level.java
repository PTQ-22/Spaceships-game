package levels;

import Button.Button;
import Entities.Bullets.Bullet;
import Entities.Player;
import Entities.Entity;
import main.KeyHandler;
import main.MouseController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Level {
    protected BufferedImage backgroundImage;
    protected Player player;
    protected Button menuButton = null;
    protected ArrayList<Entity> enemiesList;
    protected MouseController mouseController;
    protected KeyHandler keyHandler;
    protected String state = "game";
    protected Font font;
    protected final int FONT_SIZE = 100;

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

    protected void checkCurrentState() {
        if (enemiesList.size() == 0) {
            state = "win";
            font = new Font("FreeSans", Font.BOLD, FONT_SIZE);
        }
        if (player.getHp() <= 0) {
            state = "lose";
            font = new Font("FreeSans", Font.BOLD, FONT_SIZE);
        }
        for (int i = 0; i < enemiesList.size(); ++i) {
            Entity e = enemiesList.get(i);
            if (player.collision(e)) {
                enemiesList.remove(i);
                --i;
                state = "lose";
                font = new Font("FreeSans", Font.BOLD, FONT_SIZE);
            }
        }
    }

    protected void checkPlayerBulletsHits() {
        // check if player bullet hits
        for (int i = 0; i < player.bullets.size(); ++i) {
            Bullet playerBullet = player.bullets.get(i);
            for (int j = 0; j < enemiesList.size(); ++j) {
                Entity e = enemiesList.get(j);
                if (playerBullet.hit(e)) {
                    player.bullets.remove(i);
                    --i;
                    e.decreaseHp(playerBullet.getPower());
                    if (e.getHp() <= 0) {
                        enemiesList.remove(j);
                        --j;
                    }
                }
            }
        }
    }

    protected void checkEnemyBulletsHits(Entity e) {
        // check if enemy bullet hits
        for (int i = 0; i < e.bullets.size(); ++i) {
            Bullet enemyBullet = e.bullets.get(i);
            if (enemyBullet.hit(player)) {
                e.bullets.remove(i);
                --i;
                player.decreaseHp(enemyBullet.getPower());
            }
        }
    }

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
