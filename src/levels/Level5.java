package levels;

import Entities.Enemies.MissileEnemy;
import main.KeyHandler;
import main.MouseController;

public class Level5 extends Level{

    public Level5(MouseController mC, KeyHandler kH) {
        super(mC, kH, 5);
        backgroundImage = loadBackgroundImage("../images/level_background/background_l1.png");
        enemiesList.add(new MissileEnemy(200, 100, 400, 40, player));
    }

}
