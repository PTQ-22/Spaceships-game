package levels;

import Entities.Enemies.RedSmall;
import main.KeyHandler;
import main.MouseController;

public class Level2 extends Level{

    public Level2(MouseController mC, KeyHandler kH) {
        super(mC, kH, 2);
        backgroundImage = loadBackgroundImage("../images/level_background/background_l1.png");
        enemiesList.add(new RedSmall(100, 100, 300, 40));
        enemiesList.add(new RedSmall(300, 300, 300, 500));
    }

}
