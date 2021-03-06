package main;

import levels.Level;
import levels.Menu;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int WIDTH = 1000;
    final int HEIGHT = 750;
    final int FPS = 60;

    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    MouseController mouseController = new MouseController();

    Level currentLevel = new Menu(mouseController, keyHandler);

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseController);
        this.addMouseMotionListener(mouseController);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1_000_000_000 / (double) FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                --delta;
            }
        }
    }

    private void update() {
        currentLevel.update();
        currentLevel = currentLevel.updateLevelType();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        currentLevel.draw(graphics2D);

        graphics2D.dispose();
        Toolkit.getDefaultToolkit().sync();
    }
}
