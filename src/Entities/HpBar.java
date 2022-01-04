package Entities;

import java.awt.*;

public class HpBar {
    private final int X, Y;
    private final int WIDTH, HEIGHT;

    private final int FONT_Y;
    private final int FONT_SIZE = 20;

    private final int BORDER_SIZE = 5;

    private int hp;

    private final Font font = new Font("FreeSans", Font.BOLD, FONT_SIZE);
    private final String TEXT;

    public HpBar(int x, int y, int hp, String text) {
        X = x;
        Y = y;
        FONT_Y = y - FONT_SIZE / 3;
        this.hp = hp;
        WIDTH = hp + 2 * BORDER_SIZE;
        HEIGHT = 20;
        TEXT = text;
    }

    public void setHp(int value) {
        hp = value;
    }

    public void draw(Graphics2D g2) {
        // text
        g2.setColor(Color.black);
        g2.setFont(font);
        g2.drawString(TEXT, X, FONT_Y);

        // border
        g2.fillRect(X, Y, WIDTH, HEIGHT);

        // hp
        g2.setColor(Color.red);
        g2.fillRect(X + BORDER_SIZE, Y + BORDER_SIZE, WIDTH - 2 * BORDER_SIZE, HEIGHT - 2 * BORDER_SIZE);
        g2.setColor(Color.green);
        g2.fillRect(X + BORDER_SIZE, Y + BORDER_SIZE, hp, HEIGHT - 2 * BORDER_SIZE);
    }
}
