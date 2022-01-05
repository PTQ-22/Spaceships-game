package levels;

import Entities.Enemies.RedSmall;
import main.KeyHandler;
import main.MouseController;

public class Level1 extends Level{

    public Level1(MouseController mC, KeyHandler kH) {
        super(mC, kH, 1);
        backgroundImage = loadBackgroundImage("../images/level_background/background_l1.png");
        enemiesList.add(new RedSmall(200, 100, 400, 40));
    }

}
