package levels;

import Button.Button;
import Entities.Bullets.Bullet;
import Entities.Player;
import Entities.Entity;
import main.KeyHandler;
import main.MouseController;
import main.StarFileController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public abstract class Level {
    protected ArrayList<Entity> enemiesList;
    protected BufferedImage backgroundImage;
    protected Player player;
    protected Button menuButton = null;
    protected MouseController mouseController;
    protected KeyHandler keyHandler;
    protected String state = "game";
    protected Font font;
    protected final int FONT_SIZE = 100;
    protected int[] stars;
    protected int levelNumber;
    protected ArrayList<Entity> boomEntities;
    protected ArrayList<Entity> gameBoomEntities;
    protected ArrayList<Hit> bulletHits = new ArrayList<>();
    protected final int NUM_OF_IMAGES = 11;
    protected BufferedImage[] boomImages = new BufferedImage[NUM_OF_IMAGES];

    public Level(MouseController mC, KeyHandler kH, int levelNumber, int[] stars) {
        this.levelNumber = levelNumber;
        mouseController = mC;
        keyHandler = kH;
        player = new Player(kH);
        enemiesList = new ArrayList<>();
        boomEntities = new ArrayList<>();
        this.stars = Arrays.copyOf(stars, 3);
        menuButton = new Button(5, 5, 25, 20, new Color(255, 0, 0), new Color(200, 0, 0), "X", 15);
        menuButton.fontPosXChange = -6;
        loadBoomImages();
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
                drawHits(g2);
                drawAllBoomAnimations(g2);
                player.drawHpBar(g2);
            }
            case "win" -> {
                drawAllBoomAnimations(g2);
                player.draw(g2);
                player.drawBullets(g2);
                player.drawHpBar(g2);
                drawHits(g2);
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
                drawHits(g2);
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
                for (int i = 0; i < enemiesList.size(); ++i) {
                    Entity e = enemiesList.get(i);
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
                for (int i = 0; i < enemiesList.size(); ++i) {
                    Entity e = enemiesList.get(i);
                    e.move();
                    e.moveBullets();
                }
            }
        }
    }

    public void addEnemy(Entity e) {
        enemiesList.add(e);
    }

    protected void checkCurrentState() {
        if (enemiesList.size() == 0 || isAllNotImportant()) {
            state = "win";
            setStars();
            font = new Font("FreeSans", Font.BOLD, FONT_SIZE);
        }
        if (player.getHp() <= 0) {
            startBoomAnimation(player);
            player = null;
            state = "lose";
            font = new Font("FreeSans", Font.BOLD, FONT_SIZE);
            return;
        }
        for (int i = 0; i < enemiesList.size(); ++i) {
            Entity e = enemiesList.get(i);
            if (e.canCollision && player.collision(e)) {
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
                    startDrawingHit(playerBullet.x, playerBullet.y, playerBullet.hitDrawScale);
                    player.bullets.remove(i);
                    --i;
                    e.decreaseHp(playerBullet.getPower());
                    if (e.getHp() <= 0) {
                        startBoomAnimation(e);
                        enemiesList.remove(j);
                        --j;
                    }
                    break;
                }
            }
        }
    }

    protected void checkEnemyBulletsHits(Entity e) {
        // check if enemy bullet hits
        for (int i = 0; i < e.bullets.size(); ++i) {
            Bullet enemyBullet = e.bullets.get(i);
            if (enemyBullet.hit(player)) {
                startDrawingHit(enemyBullet.x, enemyBullet.y, enemyBullet.hitDrawScale);
                e.bullets.remove(i);
                --i;
                player.decreaseHp(enemyBullet.getPower());
            }
        }
    }
    
    protected class Hit {
        private final BufferedImage[] hitImages = Arrays.copyOf(boomImages, NUM_OF_IMAGES);
        private final int x, y;
        private int boomCounter = 0;
        public boolean isBoom = true;

        public Hit(int x, int y, double scale) {
            this.x = x - 5;
            this.y = y;
            scaleBoomImages(scale);
        }

        public void drawBoomAnimation(Graphics2D g2) {
            boomCounter++;
            if (boomCounter >= 20) {
                isBoom = false;
            }
            g2.drawImage(hitImages[boomCounter / 2], x, y, null);
        }

        protected void scaleBoomImages(double scale) {
            for (int i = 0; i < hitImages.length; ++i) {
                int w = hitImages[i].getWidth();
                int h = hitImages[i].getHeight();
                int integerScale = (int)Math.ceil(scale);
                BufferedImage after = new BufferedImage(w * integerScale, h * integerScale, BufferedImage.TYPE_INT_ARGB);
                AffineTransform at = new AffineTransform();
                at.scale(scale, scale);
                AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                after = scaleOp.filter(hitImages[i], after);
                hitImages[i] = after;
            }
        }
    }
    
    protected void startDrawingHit(int x, int y, double scale) {
        bulletHits.add(new Hit(x, y, scale));
    }

    protected void drawHits(Graphics2D g2) {
        for (int i = 0; i < bulletHits.size(); ++i) {
            Hit h = bulletHits.get(i);
            h.drawBoomAnimation(g2);
            if (!h.isBoom) {
                bulletHits.remove(i);
                --i;
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

    protected void loadBoomImages() {
        for (int i = 1; i <= NUM_OF_IMAGES; ++i) {
            boomImages[i - 1] = loadBackgroundImage("/boom_animation/boom_" + i + ".png");
        }
    }

    protected boolean isAllNotImportant() {
        for (Entity e: enemiesList) {
            if (e.canCollision) return false;
        }
        return true;
    }
}
