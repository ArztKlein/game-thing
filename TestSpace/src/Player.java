//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Player {
    private final double y;
    private double x;
    private int direction;
    private int health;
    private final Image sprite;
    private final double speed;
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private Weapon selectedWeapon;
    private int selectedWeaponIndex;

    public Player(int x, int y, Image sprite) {
        this.x = x;
        this.y = y;
        this.direction = 0;
        this.speed = 400.0D;
        this.health = 100;
        this.sprite = sprite;

        // Give weapons
        weapons.add(new MachineGun(this));
        weapons.add(new Flamethrower(this));
        // Select the machine gun
        selectWeapon(0);
    }

    public void draw(TestSpace game) {
        Graphics2D g = game.mGraphics;
        g.drawImage(sprite, (int)x, (int)y, sprite.getWidth(null)*2, sprite.getHeight(null)*2, null);
        selectedWeapon.draw(game);
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

        selectedWeapon.update(dt);
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

    private void selectWeapon(int slot) {
        // Make sure old weapon stops shooting first
        if (selectedWeapon != null) stopShooting();
        // Select
        selectedWeapon = weapons.get(slot);
        selectedWeaponIndex = slot;
    }

    public boolean isShooting() {
        return selectedWeapon.isShooting();
    }
    public void startShooting(){
        selectedWeapon.startShooting();
    }
    public void stopShooting(){
        selectedWeapon.stopShooting();
    }

    public void selectNextWeapon() {
        // Increment counter
        int nextIndex = (selectedWeaponIndex + 1) % weapons.size();
        selectWeapon(nextIndex);
    }

    public void selectPrevWeapon() {
        // Decrement counter
        int prevIndex = selectedWeaponIndex - 1;
        if (prevIndex == -1) prevIndex = weapons.size() - 1;
        // Select
        selectWeapon(prevIndex);
    }
}
