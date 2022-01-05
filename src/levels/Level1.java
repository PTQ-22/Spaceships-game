package levels;

import Entities.Enemies.RedSmall;
import Entities.Entity;
import Entities.Player;
import main.KeyHandler;
import main.MouseController;

import java.awt.*;
import java.util.ArrayList;

public class Level1 extends Level{

    public Level1(MouseController mC, KeyHandler kH) {
        super(mC, kH);
        backgroundImage = loadBackgroundImage("../images/level_background/background_l1.png");
        player = new Player(kH);
        enemiesList = new ArrayList<>();
        enemiesList.add(new RedSmall(200, 100));
    }

    @Override
    public void draw(Graphics2D g2) {
        drawBackground(g2);
        switch (state) {
            case "game" -> {
                player.draw(g2);
                player.drawBullets(g2);
                for (Entity e : enemiesList) {
                    e.draw(g2);
                    e.drawBullets(g2);
                }
            }
            case "win" -> {
                player.draw(g2);
                player.drawBullets(g2);
                g2.setColor(Color.green);
                g2.setFont(font);
                g2.drawString("WIN", 390, 300);
            }
            case "lose" -> {
                for (Entity e : enemiesList) {
                    e.draw(g2);
                    e.drawBullets(g2);
                }
                g2.setColor(new Color(230, 0, 0));
                g2.setFont(font);
                g2.drawString("LOSE", 360, 300);
            }
        }
    }

    @Override
    public void update() {

        switch (state) {
            case "game":
                player.move();
                player.moveBullets();
                checkPlayerBulletsHits();
                for(Entity e : enemiesList) {
                    e.move();
                    e.moveBullets();
                    checkEnemyBulletsHits(e);
                }
                checkCurrentState();
                break;
            case "win":
                player.move();
                player.moveBullets();
                break;
            case "lose":
                for(Entity e : enemiesList) {
                    e.move();
                    e.moveBullets();
                    checkEnemyBulletsHits(e);
                }
                break;
        }
    }

}
