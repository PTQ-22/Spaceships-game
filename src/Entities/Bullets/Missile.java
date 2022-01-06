package Entities.Bullets;

public class Missile extends Bullet {
    private final int TARGET_X;

    public Missile(int x, int y, String imgPathName, boolean goDown, int playerX) {
        super(x, y, imgPathName, goDown);
        TARGET_X = playerX + 50;
        this.speed = 12;
        this.power = 50;
    }

    @Override
    public void move() {
        if (x > TARGET_X + 5) x -= speed;
        else if (x < TARGET_X - 5) x += speed;
        y += speed;
    }
}
