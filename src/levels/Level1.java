package levels;

import Entities.Player;
import main.KeyHandler;
import main.MouseController;

import java.awt.*;

public class Level1 extends Level{

    public Level1(MouseController mC, KeyHandler kH) {
        super(mC, kH);
        backgroundImage = loadBackgroundImage("../images/level_background/background_l1.png");
        player = new Player(kH);
    }

    @Override
    public void draw(Graphics2D g2) {
        drawBackground(g2);
        player.draw(g2);
    }

    @Override
    public void update() {
        player.move();
    }

}
