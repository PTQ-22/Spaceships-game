package levels;

import Entities.Enemies.RedSmall;
import main.KeyHandler;
import main.MouseController;

public class Level3 extends Level{

    public Level3(MouseController mC, KeyHandler kH) {
        super(mC, kH, 3);
        backgroundImage = loadBackgroundImage("../images/level_background/background_l1.png");
        enemiesList.add(new RedSmall(200, 100, 250, 40));
        enemiesList.add(new RedSmall(300, 250, 250, 340));
        enemiesList.add(new RedSmall(600, 400, 250, 640));
    }
}
