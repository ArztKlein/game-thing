import java.util.Timer;

public abstract class Weapon {
    protected int rateOfFire;
    protected int ammoCapacity;
    protected int availableRounds;
    protected double x, y;
    protected double lastShotTime;
    protected boolean isShooting;
    protected Player player;

    public Weapon(Player player){
        this.player = player;
        x = player.getX()+5;
        y = player.getY()-5;
        this.lastShotTime= 0;
    }
    public void update(double dt){
        x = (player.getX()+5);
        y = (player.getY()-5);

        if (isShooting && availableRounds > 0) {
            double currentTime = System.currentTimeMillis();
            double timeSinceLastShot = currentTime - lastShotTime;

            if (lastShotTime == 0) { // Fire immediately on the first shot
                fire();
                lastShotTime = currentTime;
            }
            else if (timeSinceLastShot >= 1000.0 / rateOfFire) {
                fire();
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
