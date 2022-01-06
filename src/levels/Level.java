package levels;

import Button.Button;
import Entities.Bullets.Bullet;
import Entities.Player;
import Entities.Entity;
import main.KeyHandler;
import main.MouseController;
import starData.StarFileController;

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
    protected int[] stars;
    protected int levelNumber;
    protected ArrayList<Entity> boomEntities;
//    protected int animationCounter;

    public Level(MouseController mC, KeyHandler kH, int levelNumber) {
        this.levelNumber = levelNumber;
        mouseController = mC;
        keyHandler = kH;
        player = new Player(kH);
        enemiesList = new ArrayList<>();
        boomEntities = new ArrayList<>();
        stars = new int[] {0, 0, 0};
        menuButton = new Button(5, 5, 25, 20, new Color(255, 0, 0), new Color(200, 0, 0), "X", 15);
        menuButton.fontPosXChange = -6;
    }

    protected Level() {} // for Menu different constructor

    public void draw(Graphics2D g2) {
        drawBackground(g2);
        switch (state) {
            case "game" -> {
                player.draw(g2);
                player.drawBullets(g2);
                for (Entity e : enemiesList) {
                    e.draw(g2);
                    e.drawBullets(g2);
                    e.drawHpBar(g2);
                }
                player.drawHpBar(g2);
            }
            case "win" -> {
                drawAllBoomAnimations(g2);
                player.draw(g2);
                player.drawBullets(g2);
                player.drawHpBar(g2);
                g2.setColor(Color.green);
                g2.setFont(font);
                g2.drawString("WIN", 390, 300);
            }
            case "lose" -> {
                drawAllBoomAnimations(g2);
                for (Entity e : enemiesList) {
                    e.draw(g2);
                    e.drawBullets(g2);
                    e.drawHpBar(g2);
                }
                g2.setColor(new Color(230, 0, 0));
                g2.setFont(font);
                g2.drawString("LOSE", 360, 300);
            }
        }
    }

    public void update() {

        switch (state) {
            case "game" -> {
                player.move();
                player.moveBullets();
                checkPlayerBulletsHits();
                for (Entity e : enemiesList) {
                    e.move();
                    e.moveBullets();
                    checkEnemyBulletsHits(e);
                }
                checkCurrentState();
            }
            case "win" -> {
                player.move();
                player.moveBullets();
            }
            case "lose" -> {
                for (Entity e : enemiesList) {
                    e.move();
                    e.moveBullets();
                    checkEnemyBulletsHits(e);
                }
            }
        }
    }

    protected void checkCurrentState() {
        if (enemiesList.size() == 0) {
            state = "win";
            setStars();
            font = new Font("FreeSans", Font.BOLD, FONT_SIZE);
        }
        if (player.getHp() <= 0) {
            startBoomAnimation(player);
            state = "lose";
            font = new Font("FreeSans", Font.BOLD, FONT_SIZE);
        }
        for (int i = 0; i < enemiesList.size(); ++i) {
            Entity e = enemiesList.get(i);
            if (player.collision(e)) {
                startBoomAnimation(player);
                startBoomAnimation(e);
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
                        startBoomAnimation(e);
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

    protected void startBoomAnimation(Entity e) {
        e.isBoom = true;
        boomEntities.add(e);
    }
    protected void drawAllBoomAnimations(Graphics2D g2) {
        for (int i = 0; i < boomEntities.size(); ++i) {
            Entity e = boomEntities.get(i);
            e.drawBoomAnimation(g2);
            if (!e.isBoom) {
                boomEntities.remove(i);
                --i;
            }
        }
    }

    public Level updateLevelType() {
        if (menuButton.isMouse(mouseController.mousePos)) {
            menuButton.changeColorToFocus();
            if (mouseController.clicked) {
                mouseController.clicked = false;

                saveStars();

                return new Menu(mouseController, keyHandler);
            }
        }
        else {
            menuButton.changeColorToDefault();
        }
        mouseController.clicked = false;

        return this;
    }

    protected void setStars() {
        stars[0] = 1;
        if (player.getHp() == player.getSTART_HP()) {
            stars[1] = 1;
        }
        if (!player.isLostBullet()) {
            stars[2] = 1;
        }
    }

    protected void saveStars() {
        StarFileController starFileController = new StarFileController();
        starFileController.updateStars(levelNumber, stars);
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
