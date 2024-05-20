//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class Player {
    private final double y;
    private double x;
    private int direction;
    private int health;
    private final Image sprite;
    private final double speed;
    private final int SIZE = 40;

    public Player(int x, int y, Image sprite) {
        this.x = x;
        this.y = y;
        this.direction = 0;
        this.speed = 400.0D;
        this.health = 100;
        this.sprite = sprite;
    }

    public void draw(TestSpace game) {
        game.drawImage(sprite, x, y, SIZE, SIZE);
    }

    public void moveLeft() {
        this.direction = -1;
    }

    public void moveRight() {
        this.direction = 1;
    }

    public void stop() {
        this.direction = 0;
    }

    public void update(double dt) {
        this.x += (double)this.direction * this.speed * dt;
        if (this.x > TestSpace.WIDTH - 50) {
            this.x = TestSpace.WIDTH - 50;
        } else if (this.x < 50) {
            this.x = 50;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }
}
