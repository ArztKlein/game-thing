import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;

public abstract class Weapon {
    protected int rateOfFire;
    protected int ammoCapacity;
    protected int availableRounds;
    protected double x, y;
    protected double lastShotTime;
    protected boolean isShooting;
    protected Player player;
    private GameEngine gameEngine;
    GameEngine.AudioClip weaponFired;
    private String name;
    private Image sprite;

    public Weapon(Player player, GameEngine g, String gunFiredFileName, String name, String imagePath){
        this.player = player;
        gameEngine = g;
        weaponFired = g.loadAudio(gunFiredFileName);
        x = player.getX();
        y = player.getY();
        this.name = name;
        this.lastShotTime= 0;
        this.sprite = GameEngine.loadImage(imagePath);
    }
    public void update(double dt){
        x = player.getX();
        y = player.getY();

        if (isShooting && availableRounds > 0) {
            double currentTime = System.currentTimeMillis();
            double timeSinceLastShot = currentTime - lastShotTime;

            if (lastShotTime == 0) { // Fire immediately on the first shot
                fire();
                gameEngine.playAudio(weaponFired);
                lastShotTime = currentTime;
            }
            else if (timeSinceLastShot >= 1000.0 / rateOfFire) {
                fire();
                gameEngine.playAudio(weaponFired);
                lastShotTime = currentTime;
            }
        }
    }
    public abstract void fire();

    public boolean isShooting(){return isShooting;}

    public String getName() {
        return name;
    }

    public void startShooting(){
        isShooting = true;
        lastShotTime = 0;
    }
    public void stopShooting(){
        isShooting = false;
    }

    public void draw(TestSpace game){}

    public int getAvailableRounds() {
        return availableRounds;
    }

    public Image getSprite() {
        return sprite;
    }

    public abstract void incrementRounds();
}
