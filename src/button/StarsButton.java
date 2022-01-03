package button;

import java.awt.*;

public class StarsButton extends Button{

    // arrays of points positions to draw stars
    private final int NUM_POINTS = 10;

    private final int[] pointsXStar1 = new int[NUM_POINTS];
    private final int[] pointsXStar2 = new int[NUM_POINTS];
    private final int[] pointsXStar3 = new int[NUM_POINTS];

    private final int[] pointsYStar1 = new int[NUM_POINTS];
    private final int[] pointsYStar2 = new int[NUM_POINTS];
    private final int[] pointsYStar3 = new int[NUM_POINTS];
    Font font = new Font("FreeSans", Font.BOLD, 30);

    public StarsButton(int x, int y, int width, int height, Color color, Color focusColor, String text) {
        super(x, y, width, height, color, focusColor, text);
        for (int i = 0; i < NUM_POINTS; ++i) {
            int[] pointsX = {0, 4, 12, 6, 8, 0, -8, -6, -12, -4};
            pointsXStar1[i] = x + 150 + pointsX[i];
            pointsXStar2[i] = x + 200 + pointsX[i];
            pointsXStar3[i] = x + 250 + pointsX[i];

            int[] pointsY = {0, 8, 10, 16, 24, 20, 24, 16, 10, 8};
            pointsYStar1[i] = y + 10 + pointsY[i];
            pointsYStar2[i] = y + 10 + pointsY[i];
            pointsYStar3[i] = y + 10 + pointsY[i];
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        // border
        int BORDER_SIZE = 5;
        g2.setColor(Color.black);
        g2.fillRect(X - BORDER_SIZE, Y - BORDER_SIZE, WIDTH + BORDER_SIZE * 2, HEIGHT + BORDER_SIZE * 2);

        // normal color
        g2.setColor(currentColor);
        g2.fillRect(X, Y, WIDTH, HEIGHT);

        g2.setColor(Color.black);
        g2.setFont(font);
        g2.drawString(TEXT, X + 15, Y + (HEIGHT / 2 + 10));

        drawStars(g2);
    }

    private void drawStars(Graphics2D g2) {
        g2.setColor(Color.yellow);
        g2.fillPolygon(pointsXStar1, pointsYStar1, NUM_POINTS);
        g2.fillPolygon(pointsXStar2, pointsYStar2, NUM_POINTS);
        g2.fillPolygon(pointsXStar3, pointsYStar3, NUM_POINTS);
    }
}
