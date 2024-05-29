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
    public Weapon(Player player, GameEngine g, String gunFiredFileName){
        this.player = player;
        gameEngine = g;
        weaponFired = g.loadAudio(gunFiredFileName);
        x = player.getX();
        y = player.getY();
        this.lastShotTime= 0;
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

    public void startShooting(){
        isShooting = true;
        lastShotTime = 0;
    }
    public void stopShooting(){
        isShooting = false;
    }

    public void draw(TestSpace game){}

}
