package levels;

import Entities.Enemies.TeleportEnemy;
import main.KeyHandler;
import main.MouseController;

public class Level6 extends Level{

    public Level6(MouseController mC, KeyHandler kH, int [] stars) {
        super(mC, kH, 6, stars);
        backgroundImage = loadBackgroundImage("../images/level_background/background_l6.png");
        enemiesList.add(new TeleportEnemy(200, 100, 400, 40));
    }
}
