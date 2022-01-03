package levels;

import main.MouseController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Level {
    BufferedImage backgroundImage;

    public abstract void draw(Graphics2D g2);

    public abstract void update(MouseController mouseController);

    protected void drawBackground(Graphics2D g2) {
        g2.drawImage(backgroundImage, 0, 0, null);
    }

    protected BufferedImage loadBackgroundImage(String path) {
        BufferedImage bg = null;
        try {
            bg = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream(path))
            );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return bg;
    }
}
