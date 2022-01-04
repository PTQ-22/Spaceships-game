package levels;

import main.KeyHandler;
import main.MouseController;

import java.awt.*;

public class Level6 extends Level{

    public Level6(MouseController mC, KeyHandler kH) {
        super(mC, kH);
        backgroundImage = loadBackgroundImage("../images/level_background/background_l6.png");
    }

    @Override
    public void draw(Graphics2D g2) {
        drawBackground(g2);
    }

    @Override
    public void update() {

    }
}
