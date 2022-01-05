package levels;

import main.KeyHandler;
import main.MouseController;

import java.awt.*;

public class Level4 extends Level{

    public Level4(MouseController mC, KeyHandler kH) {
        super(mC, kH, 4);
        backgroundImage = loadBackgroundImage("../images/level_background/background_l1.png");
    }
}
