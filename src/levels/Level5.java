package levels;

import main.KeyHandler;
import main.MouseController;

import java.awt.*;

public class Level5 extends Level{

    public Level5(MouseController mC, KeyHandler kH) {
        super(mC, kH, 5);
        backgroundImage = loadBackgroundImage("../images/level_background/background_l1.png");
    }

    @Override
    public void draw(Graphics2D g2) {
        drawBackground(g2);
    }

    @Override
    public void update() {

    }
}
